package zhuazhu.readhub.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

import timber.log.Timber;
import zhuazhu.readhub.BuildConfig;
import zhuazhu.readhub.di.component.AppComponent;
import zhuazhu.readhub.di.component.DaggerAppComponent;
import zhuazhu.readhub.di.module.AppModule;
import zhuazhu.readhub.di.module.NetModule;
import zhuazhu.readhub.log.DebugLogTree;
import zhuazhu.readhub.log.ReleaseLogTree;

/**
 * @author zhuazhu
 **/
public class ReadHubApp extends Application {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setEnableHeaderTranslationContent(false);
            return new MaterialHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            layout.setFooterTriggerRate(0);
            return new FalsifyFooter(context);
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initBugly();
        initLog();
        initDb();
        /**
         * 初始化X5浏览器
         */
        QbSdk.initX5Environment(getApplicationContext(),null);
    }
    private void initDb(){
        FlowManager.init(this);
    }
    private void initLog(){
        if(BuildConfig.DEBUG){
            Timber.plant(new DebugLogTree());
        }else{
            Timber.plant(new ReleaseLogTree());
        }
    }
    private void initBugly() {
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 2000;
        Beta.autoCheckUpgrade = false;
        Bugly.init(getApplicationContext(), Config.BUGLY_APP_ID, BuildConfig.DEBUG);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        Beta.installTinker();
    }

    private static AppComponent mAppComponent;
    public static AppComponent getAppComponent(){
        if(mAppComponent==null){
            mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(Utils.getApp())).netModule(new NetModule()).build();
        }
        return mAppComponent;
    }

}
