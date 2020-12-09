package net.onest.moment.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.onest.moment.R;
import net.onest.moment.activity.MainActivity;
import net.onest.moment.activity.SQMainActivity;
import net.onest.moment.activity.SettingActivity;
import net.onest.moment.manager.DefaultAttribute;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*zx：我的Fragment*/
public class FifthFragment extends Fragment {

    private ImageView imageView;
    private TextView tvMineSeting;
    private final int REQUEST_IMAGE =0;
    private final int INTENT_IMG = 20;
    private RelativeLayout root;
    private View popupView;
    PopupWindow popupWindow;
    private OkHttpClient okHttpClient;
    private File uploadFile;
    private String fileName;
    private String fileAlbumpath;
    private TextView userName;

    private TextView myLike;
    /**
     * 请求裁剪码
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCROP = 1;
    /**
     * 请求相机码
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 2;
    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 3;

    /**
     * 去上传拍照文件
     */
    protected static final int TO_UPLOAD_FILE = 4;

    /**
     * 上传相册文件
     */
    protected static final int UPLOAD_FILE_DONE = 5;

    private Uri origUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zx_fragment_fifth,
                container,
                false);
        root = view.findViewById(R.id.root);
        tvMineSeting = view.findViewById(R.id.tv_mine_set);
        myLike = view.findViewById(R.id.tv_mine_like);
        imageView = view.findViewById(R.id.iv_headPic);
        userName = view.findViewById(R.id.tv_userName);
        userName.setText(DefaultAttribute.DEFAULT_userNAME);

        //显示默认头像
        RequestOptions requestOptions = new RequestOptions()
                .circleCrop();
        Glide.with(this)
                .asBitmap()
                .load(DefaultAttribute.DEFAULT_userHEAD)
                .apply(requestOptions)
                .into(imageView);

        okHttpClient = new OkHttpClient();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(root);
            }
        });

        myLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SQMainActivity.class);
                startActivity(intent);
            }
        });
        tvMineSeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showPopupWindow(View parent){
        //设置它的视图
        final View popUpView = getActivity()
                .getLayoutInflater()
                .inflate(R.layout.zx_changeheadpic_popupwindow,null);
        //创建popupwindow对象
        popupWindow = new PopupWindow(popUpView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,true);

        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.setContentView(popUpView);
        //显示PopuoWindow，必须指定显示位置
        popupWindow.showAtLocation(root, Gravity.CENTER,0,0);

        //设置视图当中控件的属性和监听器
        Button btnCancel = popUpView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭弹出窗
                popupWindow.dismiss();
            }
        });
        Button btnFromAulm = popUpView.findViewById(R.id.btn_fromAlbum);//相册
        btnFromAulm.setOnClickListener(new View.OnClickListener() {
            //相册
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 1);
                //相册
                Intent intentAbulm = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentAbulm.setType("image/*");
                startActivityForResult(intentAbulm,REQUEST_CODE_GETIMAGE_BYSDCARD);
                popupWindow.dismiss();
            }
        });
        Button btnPhotoGragh = popUpView.findViewById(R.id.btn_fromPhotoGraph);//照相
        btnPhotoGragh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //照相
                photo();
                popupWindow.dismiss();
            }
        });
    }

    /*
    * 照相
    * */
    protected void photo() {

        //创建图片名称
        String timeStap = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        fileName = getContext().getFilesDir()+"/"+timeStap+".jpg";//照片命名
        //自定义图片保存路径
        uploadFile = new File(fileName);
        try {
            uploadFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(uploadFile);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.e("FifthFragment",uri.toString());
        openCameraIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,uri);
        startActivityForResult(openCameraIntent,
                REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    public void uploadImg(Bitmap bitmap){
        Log.e("保存头像","????");

        try {
            OutputStream outputStream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"),uploadFile);
        Log.e("请求文件体", fileBody.toString());
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file","image.jpg",fileBody)
                .addFormDataPart("email", DefaultAttribute.DEFAULT_userEMAIL)
                .build();
//        创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为Post
                .url("http://123.57.63.212:8080/yike/user/updateHead")
                .build();

        //创建callduix
        final Call call = okHttpClient.newCall(request);
        Log.e("----------", "请求前");

        //异步请求网络
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //强求失败时回调
                Log.e("请求结果", "失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功时回调
                Log.e("异步请求的结果",response.body().string());
            }
        });
        Log.e("----------", "请求后");
    }


    public void uploadAbulmImg(Bitmap bitmap){
        Log.e("上传相册头像","uploadAbulm");
        try {
            OutputStream outputStream = new FileOutputStream(fileAlbumpath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"),uploadFile);
        Log.e("请求文件体", fileBody.toString());
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file","image.jpg",fileBody)
                .addFormDataPart("email",DefaultAttribute.DEFAULT_userEMAIL)
                .build();
//        创建请求对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为Post
                .url("http://123.57.63.212:8080/yike/user/updateHead")
                .build();

        //创建callduix
        final Call call = okHttpClient.newCall(request);
        Log.e("----------", "请求前");

        //异步请求网络
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //强求失败时回调
                Log.e("请求结果", "失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功时回调
                Log.e("异步请求的结果",response.body().string());
            }
        });
        Log.e("----------", "请求后");
    }

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case TO_UPLOAD_FILE:
                    Log.e("保存头像","????");
                    uploadImg((Bitmap) msg.obj);
                    break;
                case UPLOAD_FILE_DONE: //上传相册文件
                    Log.e("上传相册头像","可以成功吗");
                    uploadAbulmImg((Bitmap) msg.obj);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //处理回调结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调处理结果
        Log.e("嘿", "1");
        //真机调试才可显示
        Log.e("响应码", requestCode+"");
        if (resultCode == Activity.RESULT_OK){
            Log.e("回调方法", "2");
            switch (requestCode){
                case REQUEST_CODE_GETIMAGE_BYCAMERA:
                    Bitmap headBitmap = null;
                    headBitmap = (Bitmap) data.getExtras().get("data");
                    RequestOptions requestOptions = new RequestOptions()
                            .circleCrop();
                    Glide.with(this)
                            .asBitmap()
                            .load(headBitmap)
                            .apply(requestOptions)
                            .into(imageView);
                    DefaultAttribute.DEFAULT_userHEAD = headBitmap;
                    //发送上传头像的信息
                    Message message = handler.obtainMessage();
                    message.what = TO_UPLOAD_FILE;
                    message.obj = headBitmap;
                    handler.sendMessage(message);
                    break;
                case REQUEST_CODE_GETIMAGE_BYSDCARD:
                    Log.e("相册","onActivityResult");
                    Bitmap bitmapAblum = null;
                    if (data==null){
                        Log.e("相册","无照片");
                    }else {
                        ContentResolver resolver = getContext().getContentResolver();
                        String[] pojo = {MediaStore.Images.Media.DATA};
                        Uri imgUri = data.getData();
                        CursorLoader cursorLoader = new CursorLoader(getContext(),imgUri,pojo,null,null,null);
                        Cursor cursor = cursorLoader.loadInBackground();
                        cursor.moveToFirst();
                        String path = cursor.getString(cursor.getColumnIndex(pojo[0]));
                        if (path!=null&&path.length()>0){
                            fileAlbumpath = path;
                            uploadFile = new File(fileAlbumpath);

                        }
                        try {
                            bitmapAblum = MediaStore.Images.Media.getBitmap(resolver,imgUri);
                            RequestOptions requestOptions1 = new RequestOptions()
                                    .circleCrop();
                            Glide.with(this)
                                    .asBitmap()
                                    .load(bitmapAblum)
                                    .apply(requestOptions1)
                                    .into(imageView);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Message message1 = handler.obtainMessage();
                    message1.what = UPLOAD_FILE_DONE;
                    message1.obj = bitmapAblum;
                    handler.sendMessage(message1);
                    break;
                default:
                    break;
            }
        }
    }


}
