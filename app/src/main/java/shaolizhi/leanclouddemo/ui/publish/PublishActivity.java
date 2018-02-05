package shaolizhi.leanclouddemo.ui.publish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import shaolizhi.leanclouddemo.R;
import shaolizhi.leanclouddemo.ui.base.BaseActivity;

public class PublishActivity extends BaseActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private Button imageSelectButton;
    private Button commitButton;
    private ImageView imageView;

    private final int REQUEST_CODE_IMAGE_SELECT = 42;

    private byte[] selectedImageData;

    //commit button click event
    private void clickCommitButton() {
        attemptCommit();
    }

    private void attemptCommit() {

        final String title = titleEditText.getText().toString();
        final String description = descriptionEditText.getText().toString();
        final String price = priceEditText.getText().toString();

        if (checkInput(title, description, price)) {
            //do something
            final ProgressDialog dialog = ProgressDialog.show(this, "提示", "正在提交中");
            AVObject product = new AVObject("Product");
            product.put("title", title);
            product.put("description", description);
            product.put("price", price);
            product.put("owner", AVUser.getCurrentUser());
            product.put("image", new AVFile("productPic", selectedImageData));
            product.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        dialog.dismiss();
                        PublishActivity.this.finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(PublishActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private Boolean checkInput(String title, String description, String price) {
        titleEditText.setError(null);
        descriptionEditText.setError(null);
        priceEditText.setError(null);


        Boolean isInputMistake = false;
        TextInputEditText inputMistakeEditText = null;

        if (TextUtils.isEmpty(price)) {
            priceEditText.setError(getString(R.string.publish_act_string10));
            inputMistakeEditText = priceEditText;
            isInputMistake = true;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError(getString(R.string.publish_act_string9));
            inputMistakeEditText = descriptionEditText;
            isInputMistake = true;
        }

        if (TextUtils.isEmpty(title)) {
            titleEditText.setError(getString(R.string.publish_act_string8));
            inputMistakeEditText = titleEditText;
            isInputMistake = true;
        }


        if (isInputMistake) {
            inputMistakeEditText.requestFocus();
        } else {
            if (selectedImageData == null) {
                Toast.makeText(PublishActivity.this, R.string.publish_act_string11, Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        }
        return false;
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
        priceEditText = findViewById(R.id.publish_act_textinputedittext3);
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
                    selectedImageData = getBytes(getContentResolver().openInputStream(data.getData()));
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
