package test.ppjun.shop.product;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import test.ppjun.shop.R;
import test.ppjun.shop.thread.ImageHttpThread;
import test.ppjun.shop.util.DataBaseHelp;


import java.util.List;

public class ProductAdapter  extends ArrayAdapter{

    private final int resourceId;


    public ProductAdapter(Context context ,int textViewResourceId ,List objects) {
        super(context ,textViewResourceId ,objects);
        resourceId = textViewResourceId;
    }


    public View getView(int position , View convertView ,ViewGroup parent) {
        final Product product = (Product) getItem(position);
        ProductLayout productLayout = new ProductLayout();
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            productLayout.titleView =(TextView) view.findViewById(R.id.title);
            productLayout.priceView = (TextView) view.findViewById(R.id.price);
            productLayout.imgView = (ImageView) view.findViewById(R.id.image);
            productLayout.addButton = (Button)view.findViewById(R.id.product_add_shop_button);
            view.setTag(productLayout);
        } else {
            view = convertView;
            productLayout = (ProductLayout) view.getTag();
        }
        productLayout.titleView.setText(product.getTitle());
        productLayout.priceView.setText(product.getPrice());

        productLayout.addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view1) {
                DataBaseHelp dataBaseHelp = new DataBaseHelp(getContext() , "demo.db" , null , 1);
                ContentValues contentValues = new ContentValues();
                Cursor cu =  dataBaseHelp.getReadableDatabase().query("demo", null, "product_id = ?",
                        new String[]{product.getId() + ""}, null, null, null);

                if(cu.moveToFirst()) {
                    contentValues.put("num" ,cu.getInt(5)+1);
                    dataBaseHelp.getWritableDatabase().update("demo", contentValues, "product_id = ? ",new String[]{product.getId()+ ""});
                }else {
                    contentValues.put("product_id" , product.getId());
                    contentValues.put("title", product.getTitle());
                    contentValues.put("price" ,product.getPrice());
                    contentValues.put("image" , product.getImage());
                    contentValues.put("num" , 1 );
                    dataBaseHelp.getWritableDatabase().insert("demo", null, contentValues);
                }

            }
        });



        ImageHttpThread imageHttpThread = new ImageHttpThread(product.getImage());
        imageHttpThread.start();

        try{
            imageHttpThread.join();
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
        productLayout.imgView.setImageBitmap(imageHttpThread.getResultBitmap());

        return view;

    }

    class ProductLayout {
        TextView titleView;
        TextView priceView;
        ImageView imgView;
        Button  addButton;
    }


}
