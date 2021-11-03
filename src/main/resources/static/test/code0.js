gdjs.New_32sceneCode = {};
gdjs.New_32sceneCode.GDLineObjects1= [];
gdjs.New_32sceneCode.GDLineObjects2= [];
gdjs.New_32sceneCode.GDBackGroundObjects1= [];
gdjs.New_32sceneCode.GDBackGroundObjects2= [];
gdjs.New_32sceneCode.GDTitleObjects1= [];
gdjs.New_32sceneCode.GDTitleObjects2= [];
gdjs.New_32sceneCode.GDObjectObjects1= [];
gdjs.New_32sceneCode.GDObjectObjects2= [];
gdjs.New_32sceneCode.GDObjectName3Objects1= [];
gdjs.New_32sceneCode.GDObjectName3Objects2= [];
gdjs.New_32sceneCode.GDObjectName2Objects1= [];
gdjs.New_32sceneCode.GDObjectName2Objects2= [];
gdjs.New_32sceneCode.GDObjectNameObjects1= [];
gdjs.New_32sceneCode.GDObjectNameObjects2= [];

gdjs.New_32sceneCode.conditionTrue_0 = {val:false};
gdjs.New_32sceneCode.condition0IsTrue_0 = {val:false};
gdjs.New_32sceneCode.condition1IsTrue_0 = {val:false};

ru = null;

gdjs.New_32sceneCode.userFunc0x6dfad0 = function(runtimeScene) {
"use strict";
runtimeScene.setBackgroundColor(100,100,240);
ru = runtimeScene;
};
gdjs.New_32sceneCode.eventsList0 = function(runtimeScene) {
	ru = runtimeScene;
{


gdjs.New_32sceneCode.condition0IsTrue_0.val = false;
{
gdjs.New_32sceneCode.condition0IsTrue_0.val = gdjs.evtTools.input.isKeyPressed(runtimeScene, "t");
}if (gdjs.New_32sceneCode.condition0IsTrue_0.val) {

}

}


{


gdjs.New_32sceneCode.userFunc0x6dfad0(runtimeScene);

}


};

gdjs.New_32sceneCode.func = function(runtimeScene) {
runtimeScene.getOnceTriggers().startNewFrame();

gdjs.New_32sceneCode.GDLineObjects1.length = 0;
gdjs.New_32sceneCode.GDLineObjects2.length = 0;
gdjs.New_32sceneCode.GDBackGroundObjects1.length = 0;
gdjs.New_32sceneCode.GDBackGroundObjects2.length = 0;
gdjs.New_32sceneCode.GDTitleObjects1.length = 0;
gdjs.New_32sceneCode.GDTitleObjects2.length = 0;
gdjs.New_32sceneCode.GDObjectObjects1.length = 0;
gdjs.New_32sceneCode.GDObjectObjects2.length = 0;
gdjs.New_32sceneCode.GDObjectName3Objects1.length = 0;
gdjs.New_32sceneCode.GDObjectName3Objects2.length = 0;
gdjs.New_32sceneCode.GDObjectName2Objects1.length = 0;
gdjs.New_32sceneCode.GDObjectName2Objects2.length = 0;
gdjs.New_32sceneCode.GDObjectNameObjects1.length = 0;
gdjs.New_32sceneCode.GDObjectNameObjects2.length = 0;

gdjs.New_32sceneCode.eventsList0(runtimeScene);
return;

}

gdjs['New_32sceneCode'] = gdjs.New_32sceneCode;


function test1(){
	gdjs.copyArray(ru.getObjects("Object"), gdjs.New_32sceneCode.GDObjectObjects1);

	for(var i = 0, len = gdjs.New_32sceneCode.GDObjectObjects1.length ;i < len;++i) 
	{
	    gdjs.New_32sceneCode.GDObjectObjects1[i].setAnimationName("Off");
	}
}

test1();
