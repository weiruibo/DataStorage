package cn.edu.gdmec.w07150837.datastoragedemo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    TextView userview;
    EditText user, password;
    Button login, regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userview = (TextView) findViewById(R.id.userview);

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        regis = (Button) findViewById(R.id.regis);

    }

    public void spWrite(View v) {

        SharedPreferences u = getSharedPreferences("user", MODE_APPEND);
        SharedPreferences.Editor editor = u.edit();

        editor.putString("NAME", user.getText().toString());
        editor.putString("PASSWORD", password.getText().toString());

        editor.commit();

        Toast.makeText(this, "SharePreferences写入成功", Toast.LENGTH_SHORT).show();

    }

    public void spRead(View v) {
        String name, password;
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        name = user.getString("NAME", "么有这个键值");
        password = user.getString("PASSWORD", "么有这个键值");
        userview.setText("账号：" + name + "\n密码：" + password);
        Toast.makeText(this, "SharePreferences读取成功", Toast.LENGTH_SHORT).show();

    }

    public void ROMRead(View v) {

        try {
            FileInputStream finput = openFileInput("user.txt");
            InputStreamReader iread = new InputStreamReader(finput);
            BufferedReader bread = new BufferedReader(iread);
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = bread.readLine()) != null) {

                sb.append(s + "\n");
            }

            finput.close();
            userview.setText(sb);
            Toast.makeText(this, "ROM读取成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ROMWrite(View v) {

        String account = user.getText().toString();
        String pass = password.getText().toString();

        try {
            FileOutputStream foutput = openFileOutput("user.txt", MODE_APPEND);
            OutputStreamWriter owrite = new OutputStreamWriter(foutput);
            BufferedWriter bw = new BufferedWriter(owrite);
            bw.write(account + ":" + pass);
            bw.flush();
            foutput.close();
            Toast.makeText(this, "ROM写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SDRead(View v) {

        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String Path = sdPath + "/user.txt";

        File file = new File(Path);
        int length = (int) file.length();

        byte b[] = new byte[length];

        try {
            FileInputStream finput = new FileInputStream(file);

            finput.read(b, 0, length);
            finput.close();
            userview.setText(new String(b));
            Toast.makeText(this, "SD卡读取成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SDWrite(View v) {

        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String Path = SDPath + "/user.txt";
        String str = user.getText().toString() + ":" + password.getText().toString();
        File file = new File(Path);

        try {
            FileOutputStream foutput = new FileOutputStream(file);
            foutput.write(str.getBytes());
            foutput.flush();
            foutput.close();
            Toast.makeText(this, "SD卡写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
