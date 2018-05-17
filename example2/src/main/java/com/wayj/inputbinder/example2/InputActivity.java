package com.wayj.inputbinder.example2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianque.inputbinder.InputBinder;
import com.tianque.inputbinder.InputBinderEngine;
import com.tianque.inputbinder.function.QueryMapFunc;
import com.tianque.inputbinder.item.ButtonInputItem;
import com.tianque.inputbinder.style.itembox.ButtonItemBox;
import com.tianque.inputbinder.style.itembox.EditItemBox;
import com.tianque.inputbinder.style.itembox.ItemBoxBase;
import com.tianque.inputbinder.style.itembox.SwitchItemBox;
import com.wayj.inputbinder.example2.model.StudentModel;

import java.util.Map;


/**
 * Created by way on 2018/3/7.
 */

public class InputActivity extends Activity {
    TextView printTxt;
    InputBinder inputBinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        createLayout();

        printTxt= findViewById(R.id.print);
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printTxt.setText(inputBinder.getRequestMap().toString().replaceAll(",",",\n"));
            }
        });
        inputBinder=new InputBinder(this);
        inputBinder.getEngine().setCallBack(new InputBinderEngine.CallBack() {
            @Override
            public void onStart(InputBinderEngine engine) {

            }
        });
        inputBinder.setRootView(this).setRelationEntity(Student.class);
//        ButtonInputItem buttonInputItem = new ButtonInputItem(R.id.input_btn,"点我一下，代码赋值");
//        buttonInputItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(InputActivity.this,"点击了一下",Toast.LENGTH_SHORT).show();
//            }
//        });
//        buttonInputItem.setDisplayText("点一下试试看");
//        inputBinder.addInputItem(buttonInputItem);

        String action =getIntent().getStringExtra("action");
        if(!TextUtils.isEmpty(action)&&action.equals("edit")){
            //模拟请求接口获得数据并显示
            doRequestAndShow();
        }
        inputBinder.start();
    }

    private void createLayout() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        layout.addView(createEditItem("name","学生姓名"));
        layout.addView(createSwitchItem("isBoy","是男孩吗"));
        layout.addView(createButtonItem("birthday","出生日期"));
        layout.addView(createButtonItem("comeDay","入学日期"));
        layout.addView(createButtonItem("vision","视力选择项"));
        layout.addView(createButtonItem("vision2","视力2_带value"));
        layout.addView(createButtonItem("multi","选择课程"));
        layout.addView(createSwitchItem("hasRoom","是否寄宿"));
        layout.addView(createEditItem("roomNumber","宿舍房间号"));
        layout.addView(createEditItem("address","住址"));
        layout.addView(createEditItem("more","备注"));
    }

    private View createEditItem(String tag,String txt) {
        EditItemBox itemBox = new EditItemBox(this);
        itemBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        itemBox.setTag(tag);
        itemBox.setTitle(txt);
        return itemBox;
    }


    private View createSwitchItem(String tag,String txt) {
        SwitchItemBox itemBox = new SwitchItemBox(this);
        itemBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        itemBox.setTag(tag);
        itemBox.setTitle(txt);
        return itemBox;
    }

    private View createButtonItem(String tag,String txt) {
        ButtonItemBox itemBox = new ButtonItemBox(this);
        itemBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        itemBox.setTag(tag);
        itemBox.setTitle(txt);
        return itemBox;
    }

    private void doRequestAndShow() {
        //request
        //get data
        //转换成实体类
//        Map<String,String> data=new HashMap<>();
//        data.put("name","hanmeimei");
//        data.put("isBoy","1");
//        data.put("birthday","1999-1-1");
//        data.put("vision","0.6");
//        data.put("student.vision2","8");
//        data.put("multi","数学");
//        inputBinder.addSavedRequestMap(data);


        inputBinder.doQuery(new QueryMapFunc() {
            @Override
            public Map doQuery() {
                return new StudentModel().getSimpleStudent();
            }
        });

//        inputBinder.doUpdate(new UpdateFunction<Student>() {
//            @Override
//            public void doUpdate(@IBQueryObject Student o) {
//                try {
//                    new StudentModel().createStudentByObj(o);
//                    //if else
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

    }
}
