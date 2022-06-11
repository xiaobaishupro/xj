package com.example.pc.updata.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseAdapter
 * @param <T>
 * @doc:BaseAdapter的封装。用于ListView,底架为RelativeLayout
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	/**
	 * 后天数据构造方法适配器
	 * @param context 上下文
	 * @param redId 资源ID,ListView→Cell Item项样式
	 */
	public MyBaseAdapter(Context context, int redId){
		this.context=context;
		this.resId=redId;
	}
	
	
	/**
	 * 先天数据构造方法适配器
	 * @param context 上下文
	 * @param redId 资源ID,ListView→Cell Item项样式
	 * @param list 数据List<T>集合
	 */
	public MyBaseAdapter(Context context, int redId, List<T> list){
		this.context=context;
		this.resId=redId;
		this.list=list;
		this.setList(list);
	}
	
	private List<T> list=new ArrayList<T>();//数据List集合
	private Context context;//上下文
	private int resId;//资源ID
	
	
	public void add(T m){
		list.add(m);
	}
	
	public void removeAll(){
		list.clear();
	}
	@Override
	public View getView(int position, View v, ViewGroup viewGroup) {
		if(v==null){
			v= LayoutInflater.from(context).inflate(resId, null);
		}

		initialData(position,v,viewGroup);
		
		return v;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	
	protected abstract void initialData(int position, View converView, ViewGroup arg2);

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		if(list==null)list=new ArrayList<T>();
		this.list = list;
	}
}
