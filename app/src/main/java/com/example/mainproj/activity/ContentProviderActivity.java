package com.example.mainproj.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproj.R;
import com.example.mainproj.adapter.ContactAdapter;
import com.example.mainproj.log.LogService;
import com.example.mainproj.vo.ContactMemberVO;

import java.util.List;

/*
    Content Provider : 애플리케이션 간의 데이터를 공유할 수 있게 해주는 인터페이스 컴포넌트
    일반적으로 사용자가 직접 만듣 앱은 콘텐트 프로바이더를 제공하고 있지 않지만
    안드로이드에 기본 탑재되어 있는 주소록, 브라우저, 통화기록, 미디어갤러리, 환경설정 등은 기본적으로 Content Provider 를 제공
    이들을 URL 방식으로 접근하여 데이터를 수정하거나 조회할 수 있다.
    사용자가 직접 Content Provider 를 이용하는 이유는 다양한데, 기본적으로는 같은 회사 앱끼리의 데이터 공유와
    AppStore 에 올릴 수 있는 앱의 최대 크기에 따른 문제 때문에 앱을 나누어서 올리는 경우가 있다.
 */

public class ContentProviderActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_CONTACT_DATA = 1;
    private Activity activity;
    private ImageButton btn_prov_back;

    private Button btn_get_address, btn_get_custom_provider;
    private RecyclerView rv_contact;

    private List<ContactMemberVO> contactMemberList;
    private ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_content_provider);

            init();
            setting();
            addListener();

        }catch (Exception e){
            LogService.error(this,e.getMessage(),e);
        }
    }
    private void init(){
        activity = this;
        btn_prov_back = findViewById(R.id.btn_prov_back);
        btn_get_address = findViewById(R.id.btn_get_contact);
        btn_get_custom_provider = findViewById(R.id.btn_get_custom_provider);
        rv_contact = findViewById(R.id.rv_content);
        contactAdapter = new ContactAdapter(activity,contactMemberList,this);
    }
    private void setting(){
        rv_contact.setAdapter(contactAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,1);
        rv_contact.setLayoutManager(gridLayoutManager);

    }
    private void addListener(){
        btn_get_address.setOnClickListener(listener_get_address);
        btn_get_custom_provider.setOnClickListener(listener_get_custom_provider);
        btn_prov_back.setOnClickListener(listener_provider_back);
    }
    private View.OnClickListener listener_provider_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private View.OnClickListener listener_get_address = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getContact();
        }
    };

    private View.OnClickListener listener_get_custom_provider = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    public void onClick(View view) {

    }

    private void setContactInfo(){
        Cursor contacts = null;
        try {
            String [] projection =
                    {
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };

            //어디서(from), 무엇을(*), 조건의(where), return, OrderBy
            contacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection,null,null,null);

            ContactMemberVO tmpContactMemberVO = new ContactMemberVO();
            contactMemberList.clear();
            while (contacts.moveToNext()){
                int idIndex = contacts.getColumnIndex(projection[0]);
                int nameIndex = contacts.getColumnIndex(projection[1]);
                int numberIndex = contacts.getColumnIndex(projection[2]);

                String id = contacts.getString(idIndex);
                String name = contacts.getString(nameIndex);
                String number = contacts.getString(numberIndex);
                tmpContactMemberVO.setId(id);
                tmpContactMemberVO.setName(name);
                tmpContactMemberVO.setNumber(number);

                contactMemberList.add(tmpContactMemberVO);
            }

            contactAdapter.notifyDataSetChanged();
        }
        catch (SecurityException securityException){
            LogService.error(activity,securityException.getMessage(),securityException);

        }
        catch (Exception e){
            LogService.error(activity,e.getMessage(),e);
            finish();
        }
        finally {
            if(contacts != null){
                contacts.close();
            }
        }
    }
    private void getContact(){
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            String[] permission={
              Manifest.permission.READ_CONTACTS
            };
            requestPermissions(permission,REQUEST_CONTACT_DATA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CONTACT_DATA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setContactInfo();
            }
        }
    }
}