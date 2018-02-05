package shaolizhi.leanclouddemo.ui.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class PublishActivity extends BaseActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText moneyEditText;
    private Button imageSelectButton;
    private Button commitButton;
    private ImageView imageView;

    private final int REQUEST_CODE_IMAGE_SELECT = 42;

    private byte[] selctedImageData;

    //commit button click event
    private void clickCommitButton() {

    }

    //image select button click event
    private void clickImageSelectButton() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_IMAGE_SELECT);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void created(Bundle bundle) {
        setActionBarTitle();
        initView();
        initListener();
    }

    private void initListener() {
        imageSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImageSelectButton();
            }
        });
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCommitButton();
            }
        });
    }

    private void initView() {
        titleEditText = findViewById(R.id.publish_act_textinputedittext1);
        descriptionEditText = findViewById(R.id.publish_act_textinputedittext2);
        moneyEditText = findViewById(R.id.publish_act_textinputedittext3);
        imageSelectButton = findViewById(R.id.publish_act_button1);
        commitButton = findViewById(R.id.publish_act_button2);
        imageView = findViewById(R.id.publish_act_imageview1);
    }

    private void setActionBarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.publish_act_string6);
        }
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, PublishActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_SELECT && resultCode == RESULT_OK) {
            try {
                if (data.getData() != null) {
                    imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                    selctedImageData = getBytes(getContentResolver().openInputStream(data.getData()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
