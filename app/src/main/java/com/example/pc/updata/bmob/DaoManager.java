package com.example.pc.updata.bmob;

import android.content.Context;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class DaoManager {
    private static final String DB_NAME = "jDatabase.db";//数据库名称
    private static DaoManager mDaoManager;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private Context context;

    public DaoManager(Context context) {
        this.context = context;
    }

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return
     */
    public static DaoManager getInstance(Context context) {
        if (mDaoManager == null) {
            synchronized (DaoManager.class) {
                if (mDaoManager == null) {
                    mDaoManager = new DaoManager(context);
                }
            }
        }
        return mDaoManager;
    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public synchronized DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     *
     * @param flag
     */
    public  void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }



    /**
     * 关闭数据库
     */
    public synchronized void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    /**
     * 判断数据库是否存在，如果不存在则创建
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        }
        return mDaoMaster;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }







    public void instNoteFrament2(NoteFrament2 myFile){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();

        myFileDao.insert(myFile);
    }

    public void delNoteFrament2(NoteFrament2 myFile){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();
        NoteFrament2 unique = myFileDao.queryBuilder().where(NoteFrament2Dao.Properties.Id.eq(myFile.getId())).build().unique();
        myFileDao.delete(unique);
    }


    public void upNoteFrament3(NoteFrament2 unique){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();
        myFileDao.update(unique);
    }

    public boolean slectHaveNoteFrament2(String bindId){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();
        NoteFrament2 users = myFileDao.queryBuilder().where(NoteFrament2Dao.Properties.ObjectId.eq(bindId)).build().unique();

        if (null==users){
            return false;
        }else {
            return true;
        }

    }

    public List<NoteFrament2> slectNoteFrament3(){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();
        List<NoteFrament2> users = myFileDao.queryBuilder().build().list();


        return users;
    }
    public List<NoteFrament2> slectNoteFrament3(Date time1,Date time2){
        NoteFrament2Dao myFileDao = getDaoSession().getNoteFrament2Dao();
        List<NoteFrament2> users = myFileDao.queryBuilder()
                .where(NoteFrament2Dao.Properties.Date.ge(time1),NoteFrament2Dao.Properties.Date.le(time2))
                .build().list();


        return users;
    }





}
