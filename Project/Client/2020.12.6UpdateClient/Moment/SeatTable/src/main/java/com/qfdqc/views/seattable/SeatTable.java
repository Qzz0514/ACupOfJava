package com.qfdqc.views.seattable;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/*座位图布局*/
public class SeatTable extends View {

    private final boolean FLAG = false;

    Paint paint = new Paint();
    Paint overviewPaint=new Paint();

    Paint lineNumberPaint; //绘制行号
    float lineNumberTxtHeight;

    //设置行号 默认显示 1,2,3....数字
    public void setLineNumbers(ArrayList<String> lineNumbers) {
        this.lineNumbers = lineNumbers;
        invalidate();
    }

    //用来保存所有行号
    ArrayList<String> lineNumbers = new ArrayList<>();

    Paint.FontMetrics lineNumberPaintFontMetrics;

    Matrix matrix = new Matrix();

    int horSpacing; //座位水平间距
    int verSpacing; //座位垂直间距
    int numberWidth; //行号宽度

    int row; //行数
    int col; //列数

    Bitmap optionalSeatBitmap; //可选座位的图片
    Bitmap checkedSeatBitmap; //选中时座位的图片
    Bitmap selectedSeatBitmap; //座位已被预约的图片

    Bitmap overviewBitmap;
    int lastX,lastY;

    int seatBitmapWidth; //整个座位图的宽度
    int seatBitmapHeight; //整个座位图的高度

    boolean isNeedDrawSeatBitmap = true; //标识是否需要绘制座位图

    float rectHeight; //概览图白色方块高度
    float rectWidth; //概览图白色方块的宽度

    float overviewHorSpacing; //概览图上方块的水平间距
    float overviewVerSpacing; //概览图上方块的垂直间距

    float overviewScale = 4.8f; //概览图的比例

    float roomHeight; //房间平铺高度

    float roomWidthScale = 0.5f; //房间默认宽度与座位图的比例

    int minRoomWidth; //房间最小宽度

    boolean isScaling; //标识是否正在缩放
    float scaleX,scaleY;
    boolean firstScale = true; //是否是第一次缩放

    int maxSelected = Integer.MAX_VALUE; //最多可以选择的座位数量

    private SeatChecker seatChecker;

    private String roomName = ""; //房间名称

    float rectW; //整个概览图的宽度
    float rectH; //整个概览图的高度

    Paint headPaint;

    Bitmap headBitmap;

    boolean isFirstDraw = true; //是否第一次执行onDraw

    boolean isDrawOverview = false; //标识是否需要绘制概览图
    boolean isDrawOverviewBitmap = true; //标识是否需要更新概览图

    int overview_checked;
    int overview_sold;
    int txt_color;
    int seatCheckedResID;
    int seatSoldResID;
    int seatAvailableResID;

    boolean isOnClick;

    private static final int SEAT_TYPE_SOLD  = 1; //座位已被预约
    private static final int SEAT_TYPE_SELECTED  = 2; //座位已被选中
    private static final int SEAT_TYPE_AVAILABLE  = 3; //座位可选
    private static final int SEAT_TYPE_NOT_AVAILABLE = 4; //座位不可用

    private int downX,downY;

    private boolean pointer;

    float headHeight; //顶部高度,可选,已选,已售区域的高度

    Paint pathPaint;
    RectF rectF;

    int borderHeight = 1; //头部下面横线的高度

    Paint redBorderPaint;

    //默认的座位图宽度,如果使用的自己的座位图片比这个尺寸大或者小,会缩放到这个大小
    private float defaultImgWidth = 40;
    //默认的座位图高度
    private float defaultImgHeight = 34;

    private int seatWidth; //座位图片的宽度
    private int seatHeight; //座位图片的高度

    public SeatTable(Context context) {
        super(context);
    }

    public SeatTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSeatTable(context,attrs);
    }

    private void initSeatTable(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SeatTableView);
        overview_checked = typedArray.getColor(R.styleable.SeatTableView_overview_checked, Color.parseColor("#5A9E64"));
        overview_sold = typedArray.getColor(R.styleable.SeatTableView_overview_sold, Color.RED);
        txt_color=typedArray.getColor(R.styleable.SeatTableView_txt_color,Color.WHITE);
        seatCheckedResID = typedArray.getResourceId(R.styleable.SeatTableView_seat_checked, R.drawable.seat_green);
        seatSoldResID = typedArray.getResourceId(R.styleable.SeatTableView_overview_sold, R.drawable.seat_sold);
        seatAvailableResID = typedArray.getResourceId(R.styleable.SeatTableView_seat_available, R.drawable.seat_gray);
        typedArray.recycle();
    }

    public SeatTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSeatTable(context,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    float xScale = 1;
    float yScale = 1;

    private void init() {
        horSpacing = (int) dip2Px(5);
        verSpacing = (int) dip2Px(10);

        minRoomWidth = (int) dip2Px(80);

        optionalSeatBitmap = BitmapFactory.decodeResource(getResources(),seatAvailableResID);

        float scaleX = defaultImgWidth / optionalSeatBitmap.getWidth();
        float scaleY = defaultImgHeight / optionalSeatBitmap.getHeight();

        xScale = scaleX;
        yScale = scaleY;

        seatHeight= (int) (optionalSeatBitmap.getHeight() * yScale);
        seatWidth= (int) (optionalSeatBitmap.getWidth() * xScale);

        checkedSeatBitmap = BitmapFactory.decodeResource(getResources(),seatCheckedResID);
        selectedSeatBitmap = BitmapFactory.decodeResource(getResources(),seatSoldResID);

        seatBitmapWidth = (int) (col * optionalSeatBitmap.getWidth() * xScale + (col - 1) * horSpacing);
        seatBitmapHeight = (int) (row * optionalSeatBitmap.getHeight() * yScale + (row - 1) * verSpacing);

        paint.setColor(Color.RED);

        numberWidth = (int) dip2Px(20);

        roomHeight = dip2Px(20);

        headHeight = dip2Px(30);

        headPaint = new Paint();
        headPaint.setStyle(Paint.Style.FILL);
        headPaint.setTextSize(24);
        headPaint.setColor(Color.WHITE);
        headPaint.setAntiAlias(true);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));

        redBorderPaint = new Paint();
        redBorderPaint.setAntiAlias(true);
        redBorderPaint.setColor(Color.RED);
        redBorderPaint.setStyle(Paint.Style.STROKE);
        redBorderPaint.setStrokeWidth(getResources().getDisplayMetrics().density * 1);

        rectF = new RectF();

        rectHeight = seatHeight / overviewScale;
        rectWidth = seatWidth / overviewScale;

        overviewHorSpacing = horSpacing / overviewScale;
        overviewVerSpacing = verSpacing / overviewScale;

        rectW = col * rectWidth + (col - 1) * overviewHorSpacing + overviewHorSpacing * 2;
        rectH = row * rectHeight + (row - 1) * overviewVerSpacing + overviewVerSpacing * 2;

        overviewBitmap = Bitmap.createBitmap((int) rectW, (int) rectH, Bitmap.Config.ARGB_4444);

        lineNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineNumberPaint.setColor(bacColor);
        lineNumberPaint.setTextSize(getResources().getDisplayMetrics().density * 16);
        lineNumberTxtHeight = lineNumberPaint.measureText("4");
        lineNumberPaintFontMetrics = lineNumberPaint.getFontMetrics();
        lineNumberPaint.setTextAlign(Paint.Align.CENTER);

        if(lineNumbers == null){
            lineNumbers = new ArrayList<>();
        }else if(lineNumbers.size() <= 0) {
            for (int i = 0; i < row; i++) {
                lineNumbers.add((i + 1) + "");
            }
        }
        matrix.postTranslate(numberWidth + horSpacing, headHeight + roomHeight + borderHeight + verSpacing);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        if (row <= 0 || col == 0) {
            return;
        }

        drawSeat(canvas);

        drawNumber(canvas);

        if (headBitmap == null) {
            headBitmap = drawHeadInfo();
        }

        canvas.drawBitmap(headBitmap, 0, 0, null);

        drawRoom(canvas);

        if (isDrawOverview) {
            long s = System.currentTimeMillis();
            if (isDrawOverviewBitmap) {
                drawOverview();
            }
            canvas.drawBitmap(overviewBitmap, 0, 0, null);
            drawOverview(canvas);
            Log.e("drawTime", "OverviewDrawTime:" + (System.currentTimeMillis() - s));
        }

        if (FLAG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d("drawTime", "totalDrawTime:" + drawTime);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int x = (int) event.getX();

        super.onTouchEvent(event);

        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            pointer = true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointer = false;
                downX = x;
                downY = y;
                isDrawOverview = true;
                handler.removeCallbacks(hideOverviewRunnable);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScaling && !isOnClick) {
                    int downDX = Math.abs(x - downX);
                    int downDY = Math.abs(y - downY);
                    if ((downDX > 10 || downDY > 10) && !pointer) {
                        int dx = x - lastX;
                        int dy = y - lastY;
                        matrix.postTranslate(dx, dy);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                handler.postDelayed(hideOverviewRunnable, 1500);
                autoScale();
                int downDX = Math.abs(x - downX);
                int downDY = Math.abs(y - downY);
                if ((downDX > 10 || downDY > 10) && !pointer) {
                    autoScroll();
                }
                break;
        }
        isOnClick = false;
        lastY = y;
        lastX = x;
        return true;
    }


    private Runnable hideOverviewRunnable = new Runnable() {
        @Override
        public void run() {
            isDrawOverview = false;
            invalidate();
        }
    };


    Bitmap drawHeadInfo() {
        String txt = "已预约";
        float txtY = getBaseLine(headPaint, 0, headHeight);
        int txtWidth = (int) headPaint.measureText(txt);
        float spacing = dip2Px(10);
        float spacing1 = dip2Px(5);
        float y = (headHeight - optionalSeatBitmap.getHeight()) / 2;

        float width = optionalSeatBitmap.getWidth() + spacing1 + txtWidth + spacing + selectedSeatBitmap.getWidth() + txtWidth + spacing1 + spacing + checkedSeatBitmap.getHeight() + spacing1 + txtWidth;

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), (int) headHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        //绘制背景
        canvas.drawRect(0, 0, getWidth(), headHeight, headPaint);
        headPaint.setColor(Color.BLACK);

        float startX = (getWidth() - width) / 2;
        tempMatrix.setScale(xScale,yScale);
        tempMatrix.postTranslate(startX,(headHeight - seatHeight) / 2);
        canvas.drawBitmap(optionalSeatBitmap, tempMatrix, headPaint);
        canvas.drawText("可选", startX + seatWidth + spacing1, txtY, headPaint);

        float selectedSeatBitmapY = startX + optionalSeatBitmap.getWidth() + spacing1 + txtWidth + spacing;
        tempMatrix.setScale(xScale,yScale);
        tempMatrix.postTranslate(selectedSeatBitmapY,(headHeight - seatHeight) / 2);
        canvas.drawBitmap(selectedSeatBitmap, tempMatrix, headPaint);
        canvas.drawText("已预约", selectedSeatBitmapY + seatWidth + spacing1, txtY, headPaint);

        float checkedSeatBitmapX = selectedSeatBitmapY + selectedSeatBitmap.getWidth() + spacing1 + txtWidth + spacing;
        tempMatrix.setScale(xScale,yScale);
        tempMatrix.postTranslate(checkedSeatBitmapX,y);
        canvas.drawBitmap(checkedSeatBitmap, tempMatrix, headPaint);
        canvas.drawText("已选中", checkedSeatBitmapX + spacing1 + seatWidth, txtY, headPaint);

        //绘制分割线
        headPaint.setStrokeWidth(1);
        headPaint.setColor(Color.GRAY);
        canvas.drawLine(0, headHeight, getWidth(), headHeight, headPaint);
        return bitmap;
    }


    //绘制中间房间
    void drawRoom(Canvas canvas) {
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#e2e2e2"));
        float startY = headHeight + borderHeight;

        float centerX = seatBitmapWidth * getMatrixScaleX() / 2 + getTranslateX();
        float roomWidth = seatBitmapWidth * roomWidthScale * getMatrixScaleX();
        if (roomWidth < minRoomWidth) {
            roomWidth = minRoomWidth;
        }

        Path path = new Path();
        path.moveTo(centerX, startY);
        path.lineTo(centerX - roomWidth / 2, startY);
        path.lineTo(centerX - roomWidth / 2 + 20, roomHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + roomWidth / 2 - 20, roomHeight * getMatrixScaleY() + startY);
        path.lineTo(centerX + roomWidth / 2, startY);

        canvas.drawPath(path, pathPaint);

        pathPaint.setColor(Color.BLACK);
        pathPaint.setTextSize(20 * getMatrixScaleX());

        canvas.drawText(roomName, centerX - pathPaint.measureText(roomName) / 2, getBaseLine(pathPaint, startY, startY + roomHeight * getMatrixScaleY()), pathPaint);
    }

    Matrix tempMatrix = new Matrix();


    //座位图实际上就是二维矩阵，有行数和列数
    //根据行数和列数加上一定的间距即可绘制
    void drawSeat(Canvas canvas) {
        zoom = getMatrixScaleX();
        long startTime = System.currentTimeMillis();
        float translateX = getTranslateX();
        float translateY = getTranslateY();
        float scaleX = zoom;
        float scaleY = zoom;

        for (int i = 0; i < row; i++) {
            float top = i * optionalSeatBitmap.getHeight() * yScale * scaleY + i * verSpacing * scaleY + translateY;

            float bottom = top + optionalSeatBitmap.getHeight() * yScale * scaleY;
            if (bottom < 0 || top > getHeight()) {
                continue;
            }

            for (int j = 0; j < col; j++) {
                float left = j * optionalSeatBitmap.getWidth() * xScale * scaleX + j * horSpacing * scaleX + translateX;

                float right = (left + optionalSeatBitmap.getWidth() * xScale * scaleY);
                if (right < 0 || left > getWidth()) {
                    continue;
                }

                int seatType = getSeatType(i, j);
                tempMatrix.setTranslate(left, top);
                tempMatrix.postScale(xScale, yScale, left, top);
                tempMatrix.postScale(scaleX, scaleY, left, top);

                switch (seatType) {
                    case SEAT_TYPE_AVAILABLE:
                        canvas.drawBitmap(optionalSeatBitmap, tempMatrix, paint);
                        break;
                    case SEAT_TYPE_NOT_AVAILABLE:
                        break;
                    case SEAT_TYPE_SELECTED:
                        canvas.drawBitmap(checkedSeatBitmap, tempMatrix, paint);
                        drawText(canvas, i, j, top, left);
                        break;
                    case SEAT_TYPE_SOLD:
                        canvas.drawBitmap(selectedSeatBitmap, tempMatrix, paint);
                        break;
                }
            }
        }

        if (FLAG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.d("drawTime", "seatDrawTime:" + drawTime);
        }
    }


    private int getSeatType(int row, int col) {
        if (isHave(getID(row, col)) >= 0) {
            return SEAT_TYPE_SELECTED;
        }

        if (seatChecker != null) {
            if (!seatChecker.isValidSeat(row, col)) {
                return SEAT_TYPE_NOT_AVAILABLE;
            } else if (seatChecker.isSold(row, col)) {
                return SEAT_TYPE_SOLD;
            }
        }
        return SEAT_TYPE_AVAILABLE;
    }

    private int getID(int row, int column) {
        return row * this.col + (column + 1);
    }


    //绘制选中座位的行号列号
    private void drawText(Canvas canvas, int row, int col, float top, float left) {
        String txtRow = (row + 1) + "排";
        String txtCol = (col + 1) + "座";

        if(seatChecker != null){
            String[] strings = seatChecker.checkedSeatTxt(row, col);
            if(strings!=null&&strings.length>0){
                if(strings.length>=2){
                    txtRow = strings[0];
                    txtCol = strings[1];
                }else {
                    txtRow = strings[0];
                    txtCol = null;
                }
            }
        }

        TextPaint txtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setColor(txt_color);
        txtPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float seatHeight = this.seatHeight * getMatrixScaleX();
        float seatWidth = this.seatWidth * getMatrixScaleX();
        txtPaint.setTextSize(seatHeight / 3);

        //获取中间线
        float center = seatHeight / 2;
        float txtWidth = txtPaint.measureText(txtRow);
        float startX = left + seatWidth / 2 - txtWidth / 2;

        //只绘制一行文字
        if(txtCol == null){
            canvas.drawText(txtRow, startX, getBaseLine(txtPaint, top, top + seatHeight), txtPaint);
        }else {
            canvas.drawText(txtRow, startX, getBaseLine(txtPaint, top, top + center), txtPaint);
            canvas.drawText(txtCol, startX, getBaseLine(txtPaint, top + center, top + center + seatHeight / 2), txtPaint);
        }
        if (FLAG) {
            Log.e("drawTest:", "top:" + top);
        }
    }

    int bacColor = Color.parseColor("#7e000000");


    //绘制行号
    void drawNumber(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        lineNumberPaint.setColor(bacColor);
        int translateY = (int) getTranslateY();
        float scaleY = getMatrixScaleY();

        rectF.top = translateY - lineNumberTxtHeight / 2;
        rectF.bottom = translateY + (seatBitmapHeight * scaleY) + lineNumberTxtHeight / 2;
        rectF.left = 0;
        rectF.right = numberWidth;
        canvas.drawRoundRect(rectF, numberWidth / 2, numberWidth / 2, lineNumberPaint);

        lineNumberPaint.setColor(Color.WHITE);

        for (int i = 0; i < row; i++) {
            float top = (i *seatHeight + i * verSpacing) * scaleY + translateY;
            float bottom = (i * seatHeight + i * verSpacing + seatHeight) * scaleY + translateY;
            float baseline = (bottom + top - lineNumberPaintFontMetrics.bottom - lineNumberPaintFontMetrics.top) / 2;
            canvas.drawText(lineNumbers.get(i),numberWidth / 2, baseline, lineNumberPaint);
        }
        if (FLAG) {
            long drawTime = System.currentTimeMillis() - startTime;
            Log.e("drawTime","drawNumberTime:" + drawTime);
        }
    }


    //绘制概览图
    void drawOverview(Canvas canvas) {
        //绘制红色框
        int left = (int) -getTranslateX();
        if (left < 0) {
            left = 0;
        }
        left /= overviewScale;
        left /= getMatrixScaleX();

        int currentWidth = (int) (getTranslateX() + (col * seatWidth + horSpacing * (col - 1)) * getMatrixScaleX());
        if (currentWidth > getWidth()) {
            currentWidth = currentWidth - getWidth();
        } else {
            currentWidth = 0;
        }

        int right = (int) (rectW - currentWidth / overviewScale / getMatrixScaleX());

        float top = -getTranslateY() + headHeight;
        if (top < 0) {
            top = 0;
        }
        top /= overviewScale;
        top /= getMatrixScaleY();
        if (top > 0) {
            top += overviewVerSpacing;
        }

        int currentHeight = (int) (getTranslateY() + (row * seatHeight + verSpacing * (row - 1)) * getMatrixScaleY());
        if (currentHeight > getHeight()) {
            currentHeight = currentHeight - getHeight();
        } else {
            currentHeight = 0;
        }

        int bottom = (int) (rectH - currentHeight / overviewScale / getMatrixScaleY());

        canvas.drawRect(left, top, right, bottom, redBorderPaint);
    }


    Bitmap drawOverview() {
        isDrawOverviewBitmap = false;
        int bac = Color.parseColor("#7e000000");
        overviewPaint.setColor(bac);
        overviewPaint.setAntiAlias(true);
        overviewPaint.setStyle(Paint.Style.FILL);
        overviewBitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(overviewBitmap);

        //绘制透明灰色背景
        canvas.drawRect(0, 0, rectW, rectH, overviewPaint);

        overviewPaint.setColor(Color.WHITE);
        for (int i = 0; i < row; i++) {
            float top = i * rectHeight + i * overviewVerSpacing + overviewVerSpacing;
            for (int j = 0; j < col; j++) {

                int seatType = getSeatType(i, j);
                switch (seatType) {
                    case SEAT_TYPE_AVAILABLE:
                        overviewPaint.setColor(Color.WHITE);
                        break;
                    case SEAT_TYPE_NOT_AVAILABLE:
                        continue;
                    case SEAT_TYPE_SELECTED:
                        overviewPaint.setColor(overview_checked);
                        break;
                    case SEAT_TYPE_SOLD:
                        overviewPaint.setColor(overview_sold);
                        break;
                }


                float left;
                left = j * rectWidth + j * overviewHorSpacing + overviewHorSpacing;
                canvas.drawRect(left, top, left + rectWidth, top + rectHeight, overviewPaint);
            }
        }
        return overviewBitmap;
    }


    //自动回弹
    /*整个大小不超过控件大小的时候，往左滑动自动回弹到右边，往右滑动自动回弹到左边，往上或往下滑动自动回弹到顶部*/
    /*整个大小超过控件大小的时候，往左滑动回弹到最右边，往右滑动回弹到最左边，往上滑动回弹到底部，往下滑动回弹到顶部*/
    private void autoScroll() {
        float currentSeatBitmapWidth = seatBitmapWidth * getMatrixScaleX();
        float currentSeatBitmapHeight = seatBitmapHeight * getMatrixScaleY();
        float moveYLength = 0;
        float moveXLength = 0;

        //处理左右滑动的情况
        if (currentSeatBitmapWidth < getWidth()) {
            if (getTranslateX() < 0 || getMatrixScaleX() < numberWidth + horSpacing) {
                //计算要移动的距离
                if (getTranslateX() < 0) {
                    moveXLength = (-getTranslateX()) + numberWidth + horSpacing;
                } else {
                    moveXLength = numberWidth + horSpacing - getTranslateX();
                }
            }
        } else {
            if (getTranslateX() < 0 && getTranslateX() + currentSeatBitmapWidth > getWidth()) { }
            else {
                //往左侧滑动
                if (getTranslateX() + currentSeatBitmapWidth < getWidth()) {
                    moveXLength = getWidth() - (getTranslateX() + currentSeatBitmapWidth);
                } else {
                    //往右侧滑动
                    moveXLength = -getTranslateX() + numberWidth + horSpacing;
                }
            }
        }

        float startYPosition = roomHeight * getMatrixScaleY() + verSpacing * getMatrixScaleY() + headHeight + borderHeight;

        //处理上下滑动
        if (currentSeatBitmapHeight + headHeight < getHeight()) {
            if (getTranslateY() < startYPosition) {
                moveYLength = startYPosition - getTranslateY();
            } else {
                moveYLength = -(getTranslateY() - (startYPosition));
            }
        } else {
            if (getTranslateY() < 0 && getTranslateY() + currentSeatBitmapHeight > getHeight()) { }
            else {
                //往上滑动
                if (getTranslateY() + currentSeatBitmapHeight < getHeight()) {
                    moveYLength = getHeight() - (getTranslateY() + currentSeatBitmapHeight);
                } else {
                    moveYLength = -(getTranslateY() - (startYPosition));
                }
            }
        }

        Point start = new Point();
        start.x = (int) getTranslateX();
        start.y = (int) getTranslateY();

        Point end = new Point();
        end.x = (int) (start.x + moveXLength);
        end.y = (int) (start.y + moveYLength);

        moveAnimate(start, end);
    }


    private void autoScale() {
        if (getMatrixScaleX() > 2.2) {
            zoomAnimate(getMatrixScaleX(), 2.0f);
        } else if (getMatrixScaleX() < 0.98) {
            zoomAnimate(getMatrixScaleX(), 1.0f);
        }
    }

    Handler handler = new Handler();

    ArrayList<Integer> selects = new ArrayList<>();

    public ArrayList<String> getSelectedSeat(){
        ArrayList<String> results=new ArrayList<>();
        for(int i = 0;i < this.row;i++){
            for(int j = 0;j < this.col;j++){
                if(isHave(getID(i,j)) >= 0){
                    results.add(i + "," + j);
                }
            }
        }
        return results;
    }


    private int isHave(Integer seat) {
        return Collections.binarySearch(selects, seat);
    }


    private void remove(int index) {
        selects.remove(index);
    }


    float[] m = new float[9];
    private float getTranslateX() {
        matrix.getValues(m);
        return m[2];
    }

    private float getTranslateY() {
        matrix.getValues(m);
        return m[5];
    }

    private float getMatrixScaleY() {
        matrix.getValues(m);
        return m[4];
    }

    private float getMatrixScaleX() {
        matrix.getValues(m);
        return m[Matrix.MSCALE_X];
    }

    private float dip2Px(float value) {
        return getResources().getDisplayMetrics().density * value;
    }


    private float getBaseLine(Paint p, float top, float bottom) {
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        int baseline = (int) ((bottom + top - fontMetrics.bottom - fontMetrics.top) / 2);
        return baseline;
    }


    private void moveAnimate(Point start, Point end) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MoveEvaluator(), start, end);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        MoveAnimation moveAnimation = new MoveAnimation();
        valueAnimator.addUpdateListener(moveAnimation);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }


    private void zoomAnimate(float cur, float tar) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(cur, tar);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        ZoomAnimation zoomAnim = new ZoomAnimation();
        valueAnimator.addUpdateListener(zoomAnim);
        valueAnimator.addListener(zoomAnim);
        valueAnimator.setDuration(400);
        valueAnimator.start();
    }

    private float zoom;
    private void zoom(float zoom) {
        float z = zoom / getMatrixScaleX();
        matrix.postScale(z, z, scaleX, scaleY);
        invalidate();
    }

    private void move(Point p) {
        float x = p.x - getTranslateX();
        float y = p.y - getTranslateY();
        matrix.postTranslate(x, y);
        invalidate();
    }


    class MoveAnimation implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Point p = (Point) animation.getAnimatedValue();
            move(p);
        }
    }


    class MoveEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
            int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
            return new Point(x, y);
        }
    }


    class ZoomAnimation implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            zoom = (Float) animation.getAnimatedValue();
            zoom(zoom);
            if (FLAG) {
                Log.e("zoomTest", "zoom:" + zoom);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }
    }


    public void setData(int row, int col) {
        this.row = row;
        this.col = col;
        init();
        invalidate();
    }


    ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            isScaling = true;
            float scaleFactor = detector.getScaleFactor();
            if (getMatrixScaleY() * scaleFactor > 3) {
                scaleFactor = 3 / getMatrixScaleY();
            }
            if (firstScale) {
                scaleX = detector.getCurrentSpanX();
                scaleY = detector.getCurrentSpanY();
                firstScale = false;
            }
            if (getMatrixScaleY() * scaleFactor < 0.5) {
                scaleFactor = 0.5f / getMatrixScaleY();
            }
            matrix.postScale(scaleFactor, scaleFactor, scaleX, scaleY);
            invalidate();
            return true;
        }


        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            isScaling = false;
            firstScale = true;
        }
    });


    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            isOnClick = true;
            int x = (int) e.getX();
            int y = (int) e.getY();
            for (int i = 0; i < row;i++) {
                for (int j = 0; j < col;j++) {
                    int tempX = (int) ((j * seatWidth + (j + 1) * horSpacing) * getMatrixScaleX() + getTranslateX());
                    int maxTemX = (int) (tempX + seatWidth * getMatrixScaleX());
                    int tempY = (int) ((i * seatHeight + i * verSpacing) * getMatrixScaleY() + getTranslateY());
                    int maxTempY = (int) (tempY + seatHeight * getMatrixScaleY());
                    if (seatChecker != null && seatChecker.isValidSeat(i, j) && !seatChecker.isSold(i, j)) {
                        if (x >= tempX && x <= maxTemX && y >= tempY && y <= maxTempY) {
                            int id = getID(i, j);
                            int index = isHave(id);
                            if (index >= 0) {
                                remove(index);
                                if (seatChecker != null) {
                                    seatChecker.unCheck(i, j);
                                }
                            } else {
                                if (selects.size() >= maxSelected) {
                                    Toast.makeText(getContext(), "最多只能选择" + maxSelected + "个座位", Toast.LENGTH_SHORT).show();
                                    return super.onSingleTapConfirmed(e);
                                } else {
                                    addChooseSeat(i, j);
                                    if (seatChecker != null) {
                                        seatChecker.checked(i, j);
                                    }
                                }
                            }
                            isNeedDrawSeatBitmap = true;
                            isDrawOverviewBitmap = true;
                            float currentScaleY = getMatrixScaleY();
                            if (currentScaleY < 1.7) {
                                scaleX = x;
                                scaleY = y;
                                zoomAnimate(currentScaleY, 1.9f);
                            }
                            invalidate();
                            break;
                        }
                    }
                }
            }
            return super.onSingleTapConfirmed(e);
        }
    });


    private void addChooseSeat(int row, int column) {
        int id = getID(row, column);
        for (int i = 0; i < selects.size(); i++) {
            int item = selects.get(i);
            if (id < item) {
                selects.add(i, id);
                return;
            }
        }
        selects.add(id);
    }


    public interface SeatChecker {
        boolean isValidSeat(int row, int col); //是否可用座位
        boolean isSold(int row, int col); //是否已售
        void checked(int row, int col);
        void unCheck(int row, int col);

        //获取选中后座位上显示的文字
        /*row是第一行的文字，col是第二行的文字，如果只返回一个元素则会显示到座位图的中间位置*/
        String[] checkedSeatTxt(int row,int col);
    }

    public void setScreenName(String screenName) {
        this.roomName = screenName;
    }

    public void setMaxSelected(int maxSelected) {
        this.maxSelected = maxSelected;
    }

    public void setSeatChecker(SeatChecker seatChecker) {
        this.seatChecker = seatChecker;
        invalidate();
    }


    private int getRowNumber(int row){
        int result = row;
        if(seatChecker == null){
            return -1;
        }

        for(int i = 0;i < row;i++){
            for (int j = 0;j < col;j++){
                if(seatChecker.isValidSeat(i,j)){
                    break;
                }
                if(j == col - 1){
                    if(i == row){
                        return -1;
                    }
                    result--;
                }
            }
        }
        return result;
    }


    private int getColumnNumber(int row,int col){
        int result = col;
        if(seatChecker == null){
            return -1;
        }

        for(int i = row;i <= row;i++){
            for (int j = 0;j < col;j++){
                if(!seatChecker.isValidSeat(i,j)){
                    if(j == col){
                        return -1;
                    }
                    result--;
                }
            }
        }
        return result;
    }


}
