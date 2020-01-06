package test.ppjun.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import test.ppjun.shop.product.Product;
import test.ppjun.shop.product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Product> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Product p = new Product();
            p.setId(i);
            p.setNum(100);
            p.setPrice("100.0");
            p.setTitle("商品" + i);
            p.setImage("http://img3.imgtn.bdimg.com/it/u=539396023,3389237282&fm=26&gp=0.jpg");
            list.add(p);
        }
        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.product, list);

        ListView listView =(ListView) findViewById(R.id.listView);
        listView.setAdapter(productAdapter);

        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
       }

        return super.onOptionsItemSelected(item);
    }
}
