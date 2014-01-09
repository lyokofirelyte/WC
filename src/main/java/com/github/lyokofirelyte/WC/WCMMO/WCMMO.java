package com.github.lyokofirelyte.WC.WCMMO;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.Events.WCMMOLevelUpEvent;
import com.github.lyokofirelyte.WCAPI.Manager.SkillManager;

public class WCMMO extends SkillManager {
	
	public WCMMO(WCAPI i) {
		super(i);
	}

	public void onLevel(WCMMOLevelUpEvent e){
		
	}
}