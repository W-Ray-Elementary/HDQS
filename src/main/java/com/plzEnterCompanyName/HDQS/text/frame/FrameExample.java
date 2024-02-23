package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager;

public class FrameExample {
    public static void main(String[] args) {

        // 创建BlockTypesetter
        BlockTypesetter separateLine1  = new BT_SeparateLine(SupportedBT_Position.UP, '-', '|');
        BlockTypesetter separateLine2  = new BT_SeparateLine(SupportedBT_Position.DOWN, '-', '|');
        BlockTypesetter separateLine3  = new BT_SeparateLine(SupportedBT_Position.LEFT, '-', '|');
        BlockTypesetter separateLine4  = new BT_SeparateLine(SupportedBT_Position.RIGHT, '-', '|');
        BlockTypesetter title5         = new BT_Tittle(SupportedBT_Position.UP, 1, ' ', true, "[未定义 undefined]");
        BlockTypesetter separateLine6  = new BT_SeparateLine(SupportedBT_Position.UP, '-', '|');
        BlockTypesetter info7          = new BT_Info(SupportedBT_Position.RIGHT, 41, 7, 1, ' ', 29, 36, 4, BlankRowStatus.AUTO);
        BlockTypesetter separateLine8  = new BT_SeparateLine(SupportedBT_Position.RIGHT, '-', '|');
        BlockTypesetter operation9     = new BT_Operation(SupportedBT_Position.DOWN, 1, ' ', 3);
        BlockTypesetter separateLine10 = new BT_SeparateLine(SupportedBT_Position.DOWN, '-', '|');
        BlockTypesetter warning11      = new BT_Warning(SupportedBT_Position.DOWN);
        BlockTypesetter text12         = new BT_Text(SupportedBT_Position.UP, 41, 10, 1, 1, ' ', 1);
        
        // 配置Layout
        Layout layout = new Layout(120, 29);
        layout.addLayer(separateLine1);
        layout.addLayer(separateLine2);
        layout.addLayer(separateLine3);
        layout.addLayer(separateLine4);
        layout.addLayer(title5);
        layout.addLayer(separateLine6);
        layout.addLayer(info7);
        layout.addLayer(separateLine8);
        layout.addLayer(operation9);
        layout.addLayer(separateLine10);
        layout.addLayer(warning11);
        layout.addLayer(text12);
        
        // 使用Layout创建Frame对象
        Frame f = new Frame(layout);
        
        // 使用MessageManager创建Message
        MessageManager mm = new MessageManager();

        mm.title("这是一帧Frame输出的内容");

        mm.text("你可以在这里写上正文");
        mm.text("正文太长时会自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行自动换行");
        mm.text();// 什么都不写就会添加空行
        mm.text("你还可以写入\n来强制\n换行");

        mm.info("这里适合写需要显示的属性");
        mm.info("属性1");
        mm.info("属性2");
        mm.info("属性3");
        mm.info("属性4");

        mm.operation("这里提示玩家进行操作");
        mm.operation("更多功能");

        // MessageManager转Message
        Message message = mm.toMessage();

        // Frame就可以输出这个message
        f.out(message);
    }
}
