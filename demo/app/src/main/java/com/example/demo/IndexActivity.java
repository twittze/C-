package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout beforeLay;//翻译之前的布局
    private NiceSpinner spLanguage;//语言选择下拉框
    private LinearLayout afterLay;//翻译之后的布局
    private TextView tvFrom;//翻译源语言
    private TextView tvTo;//翻译目标语言

    private EditText edContent;//输入框（要翻译的内容）
    private ImageView ivClearTx;//清空输入框按钮
    private TextView tvTranslation;//翻译

    private LinearLayout resultLay;//翻译结果布局
    private TextView tvResult;//翻译的结果
    private ImageView ivCopyTx;//复制翻译的结果

    private String fromLanguage = "auto";//目标语言
    private String toLanguage = "auto";//翻译语言

    private ClipboardManager myClipboard;//复制文本
    private ClipData myClip; //剪辑数据

    private String appId = "20240106001932625";
    private String key = "RDK5XoEcbSZ53fVKVlNB";


    private List<String> data = new LinkedList<>(Arrays.asList(
            "自动检测语言", "中文 → 英文", "英文 → 中文",
            "中文 → 繁体中文", "中文 → 粤语", "中文 → 日语",
            "中文 → 韩语", "中文 → 法语", "中文 → 俄语",
            "中文 → 阿拉伯语", "中文 → 西班牙语 ", "中文 → 意大利语"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        initView();
    }


    private void initView() {


        beforeLay = findViewById(R.id.before_lay);
        spLanguage = findViewById(R.id.sp_language);
        afterLay = findViewById(R.id.after_lay);
        tvFrom = findViewById(R.id.tv_from);
        tvTo = findViewById(R.id.tv_to);
        edContent = findViewById(R.id.ed_content);
        ivClearTx = findViewById(R.id.iv_clear_tx);
        tvTranslation = findViewById(R.id.tv_translation);
        resultLay = findViewById(R.id.result_lay);
        tvResult = findViewById(R.id.tv_result);
        ivCopyTx = findViewById(R.id.iv_copy_tx);


        ivClearTx.setOnClickListener(this);
        ivCopyTx.setOnClickListener(this);
        tvTranslation.setOnClickListener(this);


        spLanguage.attachDataSource(data);
        editTextListener();
        spinnerListener();

        myClipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);

    }


    private void editTextListener() {
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivClearTx.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivClearTx.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                ivClearTx.setVisibility(View.VISIBLE);

                String content = edContent.getText().toString().trim();
                if (content.isEmpty()) {
                    resultLay.setVisibility(View.GONE);
                    tvTranslation.setVisibility(View.VISIBLE);
                    beforeLay.setVisibility(View.VISIBLE);
                    afterLay.setVisibility(View.GONE);
                    ivClearTx.setVisibility(View.GONE);
                }
            }
        });
    }


    private void spinnerListener() {
        spLanguage.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {

            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                switch (position) {
                    case 0://自动检测
                        fromLanguage = "auto";
                        toLanguage = fromLanguage;
                        break;
                    case 1://中文 → 英文
                        fromLanguage = "zh";
                        toLanguage = "en";
                        break;
                    case 2://英文 → 中文
                        fromLanguage = "en";
                        toLanguage = "zh";
                        break;
                    case 3://中文 → 繁体中文
                        fromLanguage = "zh";
                        toLanguage = "cht";
                        break;
                    case 4://中文 → 粤语
                        fromLanguage = "zh";
                        toLanguage = "yue";
                        break;
                    case 5://中文 → 日语
                        fromLanguage = "zh";
                        toLanguage = "jp";
                        break;
                    case 6://中文 → 韩语
                        fromLanguage = "zh";
                        toLanguage = "kor";
                        break;
                    case 7://中文 → 法语
                        fromLanguage = "zh";
                        toLanguage = "fra";
                        break;
                    case 8://中文 → 俄语
                        fromLanguage = "zh";
                        toLanguage = "ru";
                        break;
                    case 9://中文 → 阿拉伯语
                        fromLanguage = "zh";
                        toLanguage = "ara";
                        break;
                    case 10://中文 → 西班牙语
                        fromLanguage = "zh";
                        toLanguage = "spa";
                        break;
                    case 11://中文 → 意大利语
                        fromLanguage = "zh";
                        toLanguage = "it";
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_clear_tx) {
            edContent.setText("");
            ivClearTx.setVisibility(View.GONE);
        } else if (v.getId() == R.id.iv_copy_tx) {
            String inviteCode = tvResult.getText().toString();
            myClip = ClipData.newPlainText("text", inviteCode);
            myClipboard.setPrimaryClip(myClip);
            showMsg("已复制");
        } else if (v.getId() == R.id.tv_translation) {
            translation();
        }
    }


    private void translation() {
        String inputTx = edContent.getText().toString().trim();

        if (!inputTx.isEmpty() || !"".equals(inputTx)) {
            tvTranslation.setText("翻译中...");
            tvTranslation.setEnabled(false);
            String salt = num(1);
            String spliceStr = appId + inputTx + salt + key;
            String sign = stringToMD5(spliceStr);

            asyncGet(inputTx, fromLanguage, toLanguage, salt, sign);
        } else {
            showMsg("请输入要翻译的内容！");
        }
    }


    private void asyncGet(String content, String fromType, String toType, String salt, String sign) {


        String httpStr = "http://api.fanyi.baidu.com/api/trans/vip/translate";
        String httpsStr = "https://fanyi-api.baidu.com/api/trans/vip/translate";

        String url = httpsStr +
                "?appid=" + appId + "&q=" + content + "&from=" + fromType + "&to=" +
                toType + "&salt=" + salt + "&sign=" + sign;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                goToUIThread(e.toString(), 0);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                goToUIThread(response.body().string(), 1);

            }
        });
    }


    private void goToUIThread(final Object object, final int key) {
        IndexActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTranslation.setText("翻译");
                tvTranslation.setEnabled(true);

                if (key == 0) {
                    showMsg("异常信息：" + object.toString());
                    Log.e("IndexActivity",object.toString());
                } else {
                    final TranslateResult result = new Gson().fromJson(object.toString(), TranslateResult.class);
                    tvTranslation.setVisibility(View.GONE);


                    if(result.getTrans_result().get(0).getDst() == null){
                        showMsg("数据为空");
                    }
                    tvResult.setText(result.getTrans_result().get(0).getDst());
                    resultLay.setVisibility(View.VISIBLE);
                    beforeLay.setVisibility(View.GONE);
                    afterLay.setVisibility(View.VISIBLE);
                    initAfter(result.getFrom(), result.getTo());
                }
            }
        });
    }


    public static String num(int a) {
        Random r = new Random(a);
        int ran1 = 0;
        for (int i = 0; i < 5; i++) {
            ran1 = r.nextInt(100);
            System.out.println(ran1);
        }
        return String.valueOf(ran1);
    }


    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }


    private void initAfter(String from, String to) {
        if (("zh").equals(from)) {
            tvFrom.setText("中文");
        } else if (("en").equals(from)) {
            tvFrom.setText("英文");
        } else if (("yue").equals(from)) {
            tvFrom.setText("粤语");
        } else if (("cht").equals(from)) {
            tvFrom.setText("繁体中文");
        } else if (("jp").equals(from)) {
            tvFrom.setText("日语");
        } else if (("kor").equals(from)) {
            tvFrom.setText("韩语");
        } else if (("fra").equals(from)) {
            tvFrom.setText("法语");
        } else if (("ru").equals(from)) {
            tvFrom.setText("俄语");
        } else if (("ara").equals(from)) {
            tvFrom.setText("阿拉伯语");
        } else if (("spa").equals(from)) {
            tvFrom.setText("西班牙语");
        } else if (("it").equals(from)) {
            tvFrom.setText("意大利语");
        }
        if (("zh").equals(to)) {
            tvTo.setText("中文");
        } else if (("en").equals(to)) {
            tvTo.setText("英文");
        } else if (("yue").equals(to)) {
            tvTo.setText("粤语");
        } else if (("cht").equals(to)) {
            tvTo.setText("繁体中文");
        } else if (("jp").equals(to)) {
            tvTo.setText("日语");
        } else if (("kor").equals(to)) {
            tvTo.setText("韩语");
        } else if (("fra").equals(to)) {
            tvTo.setText("法语");
        } else if (("ru").equals(to)) {
            tvTo.setText("俄语");
        } else if (("ara").equals(to)) {
            tvTo.setText("阿拉伯语");
        } else if (("spa").equals(to)) {
            tvTo.setText("西班牙语");
        } else if (("it").equals(to)) {
            tvTo.setText("意大利语");
        }
    }


    private void showMsg(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.regmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override//菜单选择项对应操作
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Drawable drawable;
        Resources res=getResources();
        if (item.getItemId() == R.id.style_red) {
            drawable = res.getDrawable(R.color.colorStRed);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.style_green) {
            drawable = res.getDrawable(R.color.colorStGreen);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.style_blue) {
            drawable = res.getDrawable(R.color.colorStBlue);
            getWindow().setBackgroundDrawable(drawable);
            return true;
        } else if (item.getItemId() == R.id.app_version) {
            AlertDialog.Builder builder = new AlertDialog.Builder(IndexActivity.this);
            builder.setTitle("版本信息")
                    .setMessage("开发者：lxk\n 日期：2023.12.21\n 浙江工业大学")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





