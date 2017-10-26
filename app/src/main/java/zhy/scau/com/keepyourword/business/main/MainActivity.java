package zhy.scau.com.keepyourword.business.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import zhy.scau.com.keepyourword.AboutUsActivity;
import zhy.scau.com.keepyourword.R;
import zhy.scau.com.keepyourword.adapter.MenuListAdapter;
import zhy.scau.com.keepyourword.business.pwnote.adapter.PwNamesAdapter;
import zhy.scau.com.keepyourword.business.pwnote.AddNewPwActivity;
import zhy.scau.com.keepyourword.db.DBHelper;
import zhy.scau.com.keepyourword.db.PWBean;
import zhy.scau.com.keepyourword.db.PWDao;
import zhy.scau.com.keepyourword.frame.FrameActivity;
import zhy.scau.com.keepyourword.popupwindow.PoupWindow;
import zhy.scau.com.keepyourword.utils.BaseConstant;
import zhy.scau.com.keepyourword.utils.BaseUtils;
import zhy.scau.com.keepyourword.utils.EncryptUtils;
import zhy.scau.com.keepyourword.utils.ScreenUtils;
import zhy.scau.com.personalmemo.bussiness.perform.widget.chart.TachographView;

public class MainActivity extends FrameActivity {

    private TachographView mTachView;

    private EditText mEdtPassWord;

    private Button mBtnTest;

    private TextView mTvPwSelect;

    private List<PWBean> mDataList;

    private PoupWindow mDropDownListPlatform;

    private ListView mLvMenus;

    private ListView mLvPwNameSelect;

    private PwNamesAdapter mPwNamesAdapter;

    private int mCurUsrId = 100;

    private int mTotalTryTimes = 100;

    private FloatingActionButton mFbtnAdd;

    private PWBean mCurSelectPwBean ;

    private String mStrToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSysInfo();

        mStrToday = iniToday();

        mTachView = (TachographView) findViewById(R.id.tachview_try_count);
        mTachView.setMCurrentColor(getResources().getColor(R.color.colorAccentDark));
        mTachView.setTheMaxNum(100);

        mEdtPassWord = (EditText) findViewById(R.id.edt_pswnote_password_input);

        mBtnTest = (Button) findViewById(R.id.btn_confirm_password);

        mTvPwSelect = (TextView) findViewById(R.id.tv_pw_select);

        mLvMenus = (ListView) findViewById(R.id.lv_menu);

        initMenuList();

        mTvPwSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDropDownListPlatform.isShowing()) {
                    mDropDownListPlatform.hide();
                } else {
                    mDropDownListPlatform.show();
                }
            }
        });

        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTachView.isAniming()){

                }else{
                    confirmPw();
                }
            }
        });


        mFbtnAdd = (FloatingActionButton) findViewById(R.id.fb_add);
        mFbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewPwActivity.class));
            }
        });
    }

    private void initMenuList() {
        String[] menuItem = new String[]{"关于我们"};
        mLvMenus.setAdapter(new MenuListAdapter(this, menuItem));
        mLvMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }
        });
    }

    private String iniToday() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String result = simpleDateFormat.format(time);
        return result;
    }


    private void reInit(){
        getTodayTryTimes();
        initData();
        initDropDownList();

        if(mDataList.size() == 0){
            mTvPwSelect.setText("");
            startActivity(new Intent(MainActivity.this, AddNewPwActivity.class));

        }else{
            mTvPwSelect.setText(mDataList.get(mPwNamesAdapter.getMCurSelect()).getPwName());
            mCurSelectPwBean = mDataList.get(mPwNamesAdapter.getMCurSelect());
        }
    }

    private void getTodayTryTimes() {
        SharedPreferences sp = getSharedPreferences("USERINFO", MODE_PRIVATE);
        int trytimes = sp.getInt(mStrToday+"trytimes", BaseConstant.ILLAGEL_INT);
        if(trytimes == BaseConstant.ILLAGEL_INT){
            mTotalTryTimes = 100;
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt(mStrToday+"trytimes",mTotalTryTimes);
            edit.commit();
        }else{
            mTotalTryTimes = trytimes;
        }
        mTachView.setCurrentNum(mTotalTryTimes);
    }

    private void getSysInfo() {
        BaseConstant.STATUS_HEIGHT = ScreenUtils.getStatusBarHeight(this);
        BaseConstant.WINDOW_HEIGHT = ScreenUtils.getScreenHeightWithVirtualBar(this);
        BaseConstant.WINDOW_WIDTH = ScreenUtils.getScreenWidth(this);
    }



    private void confirmPw() {
        String pw = mEdtPassWord.getText().toString();
        if (TextUtils.isEmpty(pw)) {
            Snackbar.make(mFbtnAdd, "输入不能为空", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
            return;
        }
        pw = EncryptUtils.md5Encrypt(pw);
        if (mTotalTryTimes > 0) {
            //TODO 对比密码
            if (confirmPwReal(pw)) {
                BaseUtils.tip(this, "恭喜圣上！","你蒙对了，赶紧牢记起来吧！");
            } else {
                mTotalTryTimes--;
                BaseUtils.tip(this, "呵呵！","你蒙错了，还有"+mTotalTryTimes +"次机会！");
                SharedPreferences sp = getSharedPreferences("USERINFO", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(mStrToday+"trytimes",mTotalTryTimes);
                edit.commit();
                mTachView.setCurrentNum(mTotalTryTimes);
            }
        } else {
            //TODO 不再允许测试
            BaseUtils.tip(this, "呵呵","今天的机会用完了，如急需机会，联系开发人员！");
        }


    }

    private boolean confirmPwReal(String pw) {
        return pw.equals(mCurSelectPwBean.getPwValue());
    }

    private void initData() {
        DBHelper helper = new DBHelper(this);
        mDataList = helper.query(new String[]{PWDao.ID, PWDao.USERID, PWDao.PWNAME, PWDao.PWVALUE},
            PWDao.USERID + "=?", new String[]{String.valueOf(mCurUsrId)}, null, null, null);
    }



    private void initDropDownList() {
        mDropDownListPlatform = new PoupWindow();
        mDropDownListPlatform.setLocation(mTvPwSelect);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drop_down_pw_list, null);

        mLvPwNameSelect = view.findViewById(R.id.lv_pws);
        mPwNamesAdapter = new PwNamesAdapter(this, mDataList);
        mLvPwNameSelect.setAdapter(mPwNamesAdapter);
        mLvPwNameSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurSelectPwBean = (PWBean) mPwNamesAdapter.getItem((int) l);
                mPwNamesAdapter.setSelectedPosi((int) l);
                mDropDownListPlatform.hide();
                onSelectedChange(mCurSelectPwBean);
            }
        });


        BaseUtils.setListViewHeightBasedOnChildren(mLvPwNameSelect);

        mDropDownListPlatform.setMaxHeight(500);
        mDropDownListPlatform.setView(view);
        mDropDownListPlatform.setOutsideTouchable(true);
    }

    private void onSelectedChange(PWBean item) {
        mTvPwSelect.setText(item.getPwName());

    }

    @Override
    protected void onResume() {
        super.onResume();
        reInit();
    }
}
