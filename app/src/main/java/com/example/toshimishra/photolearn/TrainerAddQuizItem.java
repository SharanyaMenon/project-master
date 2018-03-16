package com.example.toshimishra.photolearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/**
 * Created by SUGANTHI on 16-03-2018.
 */

public class TrainerAddQuizItem extends AppCompatActivity {
    TextView text_ls,text_q;
    Button button;
    EditText et_question,et_opt1,et_opt2,et_opt3,et_opt4,ansExp;
    RadioButton rb_ans1,rb_ans2,rb_ans3,rb_ans4;

    private static final int SELECT_PHOTO = 100;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    ImageView imageView;
    String url ;
    int ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_add_quizitem);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        text_ls = (TextView)findViewById(R.id.title_LS);
        text_q = (TextView)findViewById(R.id.title_Q);
        text_ls.setText(State.getCurrentSession().getCourseCode());
        text_q.setText(State.getCurrentQuizTitle().getTitle());

        et_question = (EditText)findViewById(R.id.xh_txt); //question
        et_opt1 = (EditText)findViewById(R.id.Opt1); //option1
        et_opt2 = (EditText)findViewById(R.id.Opt2); //option2
        et_opt3 = (EditText)findViewById(R.id.Opt3); //option3
        et_opt4 = (EditText)findViewById(R.id.Opt4); //option4

        rb_ans1 = (RadioButton)findViewById(R.id.radioButton3);
        rb_ans2 = (RadioButton)findViewById(R.id.radioButton4);
        rb_ans3 = (RadioButton)findViewById(R.id.radioButton5);
        rb_ans4 = (RadioButton)findViewById(R.id.radioButton6);

        ansExp = (EditText)findViewById(R.id.Exp);

        button = (Button)findViewById(R.id.bt_Add);
        rb_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans = 1 ;
            }
        });
        rb_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans =  2;
            }
        });
        rb_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans = 3 ;
            }
        });
        rb_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans = 4 ;
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ques = et_question.getText().toString();
                String opt1 = et_opt1.getText().toString();
                String opt2 = et_opt2.getText().toString();
                String opt3 = et_opt3.getText().toString();
                String opt4 = et_opt4.getText().toString();
                String answerExp = ansExp.getText().toString();

                new Trainer().createQuizItem(url,ques,opt1,opt2,opt3,opt4,ans,answerExp);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(TrainerAddQuizItem.this, "Image selected, click on upload button", Toast.LENGTH_SHORT).show();
                    selectedImage = imageReturnedIntent.getData();
                    imageView = (ImageView) findViewById(R.id.img);
                    imageView.setImageURI(selectedImage);
                }
        }
    }

    public void selectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }



    public void uploadImage(View view) {
        //create reference to images folder and assing a name to the file that will be uploaded
        imageRef = storageRef.child("images/" + selectedImage.getLastPathSegment());
        //creating and showing progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //starting upload
        uploadTask = imageRef.putFile(selectedImage);
        // Observe state change events such as progress, pause, and resume
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);
            }
        });
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(TrainerAddQuizItem.this, "Error in uploading!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                url = downloadUrl.toString();
                Log.i("downloadURL", "download:" + downloadUrl);
                Toast.makeText(TrainerAddQuizItem.this, "Upload successful", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //showing the uploaded image in ImageView using the download url
                Log.i("ImageView", "image:" + imageView);
                Picasso.with(TrainerAddQuizItem.this).load(downloadUrl).into(imageView);
            }
        });

    }

}
