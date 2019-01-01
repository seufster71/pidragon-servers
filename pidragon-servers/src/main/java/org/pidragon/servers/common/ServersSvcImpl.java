package org.pidragon.servers.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pidragon.servers.model.Switch;
import org.pidragon.servers.model.Tower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.core.common.UtilSvc;
import org.toasthub.core.general.handler.ServiceProcessor;
import org.toasthub.core.general.model.GlobalConstant;
import org.toasthub.core.general.model.RestRequest;
import org.toasthub.core.general.model.RestResponse;


@Service("ServersSvc")
public class ServersSvcImpl implements ServiceProcessor, ServersSvc {

	@Autowired 
	UtilSvc utilSvc;
	
	private static Map<Integer,Tower> towers = new LinkedHashMap<Integer,Tower>();

	private static Switch switch1_1 = new Switch("1-1","OFF");
	private static Switch switch1_2 = new Switch("1-2","OFF");
	private static Switch switch1_3 = new Switch("1-3","OFF");
	private static Switch switch1_4 = new Switch("1-4","OFF");
	private static Switch switch1_5 = new Switch("1-5","OFF");
	private static Switch switch1_6 = new Switch("1-6","OFF");
	private static Switch switch1_7 = new Switch("1-7","OFF");
	private static Switch switch1_8 = new Switch("1-8","OFF");
	
	private static Switch switch2_1 = new Switch("2-1","OFF");
	private static Switch switch2_2 = new Switch("2-2","OFF");
	private static Switch switch2_3 = new Switch("2-3","OFF");
	private static Switch switch2_4 = new Switch("2-4","OFF");
	private static Switch switch2_5 = new Switch("2-5","OFF");
	private static Switch switch2_6 = new Switch("2-6","OFF");
	private static Switch switch2_7 = new Switch("2-7","OFF");
	private static Switch switch2_8 = new Switch("2-8","OFF");
	
	public ServersSvcImpl() {
		super();
		
		Tower tower1 = new Tower("1");
		List<Switch> switches1 = new ArrayList<Switch>();
		switches1.add(switch1_1);
		switches1.add(switch1_2);
		switches1.add(switch1_3);
		switches1.add(switch1_4);
		switches1.add(switch1_5);
		switches1.add(switch1_6);
		switches1.add(switch1_7);
		switches1.add(switch1_8);
		tower1.setSwitches(switches1);
		towers.put(1,tower1);
		
		Tower tower2 = new Tower("2");
		List<Switch> switches2 = new ArrayList<Switch>();
		switches2.add(switch2_1);
		switches2.add(switch2_2);
		switches2.add(switch2_3);
		switches2.add(switch2_4);
		switches2.add(switch2_5);
		switches2.add(switch2_6);
		switches2.add(switch2_7);
		switches2.add(switch2_8);
		tower2.setSwitches(switches2);
		towers.put(2,tower2);

	}
	
	@Override
	public void process(RestRequest request, RestResponse response) {
		String action = (String) request.getParams().get(GlobalConstant.ACTION);
		
		
		switch (action) {
		case "INIT":
			request.addParam("appPageParamLoc", "response");
			status(request,response);
			
			break;
		case "ON":
			System.out.println("ON");
			switchOn(request,response);
			break;
		case "OFF":
			System.out.println("OFF");
			switchOff(request, response);
			break;
		default:
			break;
		}
	}

	public void switchOff(RestRequest request, RestResponse response) {
		List<String> switchIds = (ArrayList<String>) request.getParams().get("switchIds");
		try {
			List<Switch> switches = new ArrayList<Switch>();
			for (String switchId : switchIds){
				String[] p = switchId.split("-");
				if (towers.containsKey(Integer.parseInt(p[0]))) {
					Switch s = getSwitch(switchId,towers.get(Integer.parseInt(p[0])).getSwitches());
					if (s != null) {
						s.setState(Switch.LOW);
						switches.add(s);
					}
				}
			}
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, "Save Successful", response);
			response.getParams().put("switches", switches);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
		}
	}
	
	public void switchOn(RestRequest request, RestResponse response) {
		List<String> switchIds = (ArrayList<String>) request.getParams().get("switchIds");
		try {
			List<Switch> switches = new ArrayList<Switch>();
			for (String switchId : switchIds){
				String[] p = switchId.split("-");
				if (towers.containsKey(Integer.parseInt(p[0]))) {
					Switch s = getSwitch(switchId,towers.get(Integer.parseInt(p[0])).getSwitches());
					if (s != null) {
						s.setState(Switch.HIGH);
						switches.add(s);
					}
				}
			}
			utilSvc.addStatus(RestResponse.INFO, RestResponse.SUCCESS, "Save Successful", response);
			response.getParams().put("switches", switches);
		} catch (Exception e) {
			utilSvc.addStatus(RestResponse.ERROR, RestResponse.ACTIONFAILED, "Save Failed", response);
		}
	}
	
	public void status(RestRequest request, RestResponse response) {
		Map<String,String> pinStatus = new HashMap<String,String>();
		
		try {
			if (request.containsParam("switchIds") ) {
				List<String> switchIds = (ArrayList<String>) request.getParams().get("switchIds");
				if ( switchIds.size() > 0) {
				}
			} else {
				List<Tower> listTowers = new ArrayList<Tower>(towers.values());
				response.addParam("towers", listTowers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void toggle(RestRequest request, RestResponse response) {
		List<String> switchIds = (ArrayList<String>) request.getParams().get("switchIds");
		
		try {
			for (String switchId : switchIds){
			//	if (gpioMap.containsKey(switchId)) {
			//		gpioMap.get(switchId).toggle();
			//	}
			}
		} catch (Exception e) {
			response.getParams().put("status", "ERROR");
			response.getParams().put("statusMsg", "Error while changing state");
		}
		response.getParams().put("response", "on");
	}
	
	private Switch getSwitch(String towerSwitch, List<Switch> switches) {
		for (Switch s : switches) {
			if (towerSwitch.equals(s.getN())){
				return s;
			}
		}
		return null;
	}
}
