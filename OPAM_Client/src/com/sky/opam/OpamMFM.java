package com.sky.opam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.loic.common.LibApplication;
import com.loic.common.fragManage.MultiFragmentManager;
import com.loic.common.fragManage.MenuElementItem;
import com.sky.opam.fragment.AgendaViewFragment;
import com.sky.opam.fragment.LoginFragment;
import com.sky.opam.model.User;
import com.sky.opam.service.IntHttpService;
import com.sky.opam.tool.DBworker;

public class OpamMFM extends MultiFragmentManager 
{
	private static final String TAG = OpamMFM.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		DBworker dBworker = DBworker.getInstance();
		User defaultUser = dBworker.getDefaultUser();
		if(defaultUser != null && defaultUser.isAutoConnect)
		{
			showGcFragment(AgendaViewFragment.class, true, null);
		}
		else 
		{
			showGcFragment(LoginFragment.class, true, null);
		}
	}
	
	@Override
	public boolean onOpenElement(MenuElementItem menuElementItem, int position)
    {
		showGcFragment(menuElementItem.fragmentClass, true, null);
		return true;
    }

	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		Intent intent = new Intent(LibApplication.getAppContext(), IntHttpService.class);
		boolean success = LibApplication.getAppContext().stopService(intent);
		Log.d(TAG, "stop INT http service ... "+success);
	}
	
}
