package zhy.scau.com.keepyourword.business.pwnote;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.List;

import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.db.DBHelper;
import zhy.scau.com.keepyourword.db.PWBean;
import zhy.scau.com.keepyourword.db.PWDao;
import zhy.scau.com.keepyourword.utils.BaseUtils;
import zhy.scau.com.keepyourword.utils.EncryptUtils;

public class AddNewPwActivity extends AppCompatActivity {

    private LinearLayout mLlNewPwPanel;

    private Button mBtnEnsure;

    private Button mBtnCancel;

    private EditText mEdtNewPwName;

    private EditText mEdtNewPwValue;

    private EditText mEdtNesConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pw);

        mLlNewPwPanel = (LinearLayout) findViewById(R.id.ll_new_pw_panel);

        mBtnEnsure = (Button) findViewById(R.id.btn_new_pw_ensure);
        mBtnCancel = (Button) findViewById(R.id.btn_new_pw_cancle);
        mEdtNewPwName = (EditText) findViewById(R.id.edt_new_pw_name);
        mEdtNewPwValue = (EditText) findViewById(R.id.edt_new_pw_value);
        mEdtNesConfirm = (EditText) findViewById(R.id.edt_new_confirm_value);

        mBtnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwName = mEdtNewPwName.getText().toString();
                String pwValue = mEdtNewPwValue.getText().toString();
                String confirmValue = mEdtNesConfirm.getText().toString();

                if(TextUtils.isEmpty(pwName)){
                    tip("报告！","密码代号为空！");
                    return ;
                }

                if(TextUtils.isEmpty(pwValue)){
                    tip("报告！","密码为空！");
                    return;
                }

                if(!pwValue.equals(confirmValue)){
                    tip("报告！","两次确认密码不一致！");
                    return;
                }

                if(checkNotUni(pwName)){
                    tip("报告！","您已经有同名的密码代号了");
                }

                addIntoDb(pwName, pwValue);
                startAnima();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addIntoDb(String pwName, String pwValue) {
        DBHelper helper = new DBHelper(this);
        pwValue = EncryptUtils.md5Encrypt(pwValue);
        helper.insert(100, pwName, pwValue);
    }

    private boolean checkNotUni(String pwName) {
        DBHelper helper = new DBHelper(this);
        List<PWBean> query = helper.query(new String[]{PWDao.ID}, PWDao.PWNAME + "=?", new String[]{pwName}, null, null, null);
        return query.size() != 0;
    }

    private void startAnima() {
        int measuredHeight = mLlNewPwPanel.getMeasuredHeight();
        final float currentY = mLlNewPwPanel.getY();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, measuredHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                mLlNewPwPanel.setY(currentY + value);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tipOk();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    private void tipOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("塞进去啦！")
            .setMessage("您重要的密码我给您加密后塞进数据库里啦！请大大地放心！！！")
            .setPositiveButton("朕已知晓，退下把！", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AddNewPwActivity.this.finish();
                }
            }).create().show();
    }

    private void tip(String title, String message){
        BaseUtils.tip(this, title, message);
    }
}
