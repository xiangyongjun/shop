package test.ppjun.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import test.ppjun.shop.product.BeanAdapter;
import test.ppjun.shop.product.Product;
import test.ppjun.shop.util.DataBaseHelp;

import java.util.ArrayList;


public class ShopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        final ArrayList<Product> arrayList = new ArrayList<>();

        final  DataBaseHelp db = new DataBaseHelp(this  ,"demo.db",null,1);
        final Cursor cursor = db.getWritableDatabase().query("demo",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Product product=new Product();
            product.setId(cursor.getInt(1));
            product.setTitle(cursor.getString(2));
            product.setPrice(cursor.getString(3));
            product.setImage(cursor.getString(4));
            product.setNum(cursor.getInt(5));
            arrayList.add(product);
        }


        final  BeanAdapter adapter = new BeanAdapter(this,R.layout.shoppingfinal,arrayList);
        final  ListView listView=(ListView)findViewById(R.id.listView2);
        listView.setAdapter(adapter);



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position , long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(arrayList.get(position)!=null){
                            DataBaseHelp dataBaseHelp=new DataBaseHelp(ShopActivity.this,"demo.db",null,1);
                            dataBaseHelp.getWritableDatabase().execSQL("delete from demo where product_id="+arrayList.get(position).getId());
                            adapter.notifyDataSetChanged();
                            arrayList.remove(position);
                            System.out.println("success");
                        } else {
                            System.out.println("failed");
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "删除列表项", Toast.LENGTH_SHORT).show();

                    }
                });


                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });


    }


}




