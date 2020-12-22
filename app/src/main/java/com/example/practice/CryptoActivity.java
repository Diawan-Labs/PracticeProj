package com.example.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.DialogCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.practice.Utility.EncryptDecrypt;
import com.example.practice.Utility.MyViewModel;
import com.example.practice.Utility.UriUtils;
import com.example.practice.databinding.ActivityCryptoBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.Arrays;

public class CryptoActivity extends AppCompatActivity {

    private static final int ENCRYPT_FILE_REQUEST_CODE = 990;
    private static final int DECRYPT_FILE_REQUEST_CODE = 991;

    private static final String KEY="myPasswordForAES";
    private static final String ENCRYPT_OPERATION="ENCRYPT";
    private static final String DECRYPT_OPERATION="DECRYPT";
    private static final int byteLength=16;
    ActivityCryptoBinding activityCryptoBinding;
    EncryptDecrypt encryptDecrypt;
    MyViewModel myViewModel;
    private static final String  TAG=CryptoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crypto);

      /*  activityCryptoBinding=ActivityCryptoBinding.inflate(getLayoutInflater());
        View view=activityCryptoBinding.getRoot();
        setContentView(view);*/

        activityCryptoBinding= DataBindingUtil.setContentView(this,R.layout.activity_crypto);
        activityCryptoBinding.setMyVariable("Hello World");
        activityCryptoBinding.consoleView.setMovementMethod(new ScrollingMovementMethod());
         myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
         myViewModel.getText().observe(this, s -> {
             activityCryptoBinding.consoleView.setText(s);
         });


        MultiplePermissionsListener multiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(this)
                        .withTitle("STORAGE permission")
                        .withMessage("STORAGE permission is needed for this App")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.ic_launcher_round)
                        .build();

        PermissionListener permissionListener=new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Log.d(TAG,"Permission Granted");
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Log.d(TAG,"Permission Denied");

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                Log.d(TAG,"Permission Denied");
                permissionToken.continuePermissionRequest();
            }
        };
        Dexter.withContext(this)
                .withPermission(Manifest.permission_group.STORAGE)
                .withListener(permissionListener)
                .check();

        encryptDecrypt=new EncryptDecrypt(KEY,byteLength,"AES");

        activityCryptoBinding.encrypt.setOnClickListener(v->pickFile(ENCRYPT_OPERATION));
        activityCryptoBinding.decrypt.setOnClickListener(v->pickFile(DECRYPT_OPERATION));
    }



    private void pickFile(String operation)
    {
        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf","application/pdf.AES",
                        "application/zip", "application/vnd.android.package-archive"};
        myViewModel.setTextView(Arrays.toString(mimeTypes));
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
        intent.setType("*/*");
     //   intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        if(operation.equalsIgnoreCase(ENCRYPT_OPERATION)) {
            startActivityForResult(intent, ENCRYPT_FILE_REQUEST_CODE);
        }
        else if(operation.equalsIgnoreCase(DECRYPT_OPERATION))
        {
            startActivityForResult(intent, DECRYPT_FILE_REQUEST_CODE);
        }
        else
            Toast.makeText(this,"invalid crypto operation",Toast.LENGTH_LONG).show();
    }

    public  void getNameFromContentUri(Context context, Uri contentUri,String Operation)
    {
       /* Cursor returnCursor = context.getContentResolver().query(contentUri, null, null, null, null);
        int nameColumnIndex = returnCursor.getColumnIndex(OpenableColumns.);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameColumnIndex);
       */
        String fullPath=UriUtils.getPathFromUri(this,contentUri);
        Log.d(TAG,"Full path::"+fullPath);
        if(Operation.equalsIgnoreCase(ENCRYPT_OPERATION))
            encryptDecrypt.encryptFile(new File(String.valueOf(fullPath)));
        else if(Operation.equalsIgnoreCase(DECRYPT_OPERATION))
            encryptDecrypt.decryptFile(new File(String.valueOf(fullPath)));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if(requestCode==ENCRYPT_FILE_REQUEST_CODE)
                getNameFromContentUri(this,data.getData(),"ENCRYPT");
            else if(requestCode==DECRYPT_FILE_REQUEST_CODE)
                getNameFromContentUri(this,data.getData(),"DECRYPT");
        }
        else
            Log.d(TAG,"RESULT CODE NOT OK");
        // Import the file

    }
}