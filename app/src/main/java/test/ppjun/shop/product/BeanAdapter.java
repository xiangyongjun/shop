package test.ppjun.shop.product;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import test.ppjun.shop.R;
import test.ppjun.shop.thread.ImageHttpThread;
import test.ppjun.shop.util.DataBaseHelp;


import java.util.ArrayList;
import java.util.List;

public class BeanAdapter extends ArrayAdapter {
    private final int resourceId;

    public BeanAdapter(Context context ,int textViewResourceId ,List objects) {
        super(context ,textViewResourceId ,objects);
        resourceId = textViewResourceId;
    }

     @Override
     public View getView(final int position , View convertView ,ViewGroup parent) {

         final Product product = (Product) getItem(position);
         ProductLayout productLayout = new ProductLayout();
         final View view;
         if(convertView == null) {
             view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
             productLayout.titleView =(TextView) view.findViewById(R.id.title);
             productLayout.priceView = (TextView) view.findViewById(R.id.price);
             productLayout.imgView = (ImageView) view.findViewById(R.id.image);
             productLayout.add = (Button)view.findViewById(R.id.add);
             productLayout.textView = (TextView) view.findViewById(R.id.number);
             productLayout.delete = (Button)view.findViewById(R.id.delete);
             view.setTag(productLayout);
         } else {
             view = convertView;
           productLayout = (ProductLayout) view.getTag();
         }

         productLayout.titleView.setText(product.getTitle());
         productLayout.priceView.setText(product.getPrice());
         productLayout.textView.setText(product.getNum() + "");


         productLayout.add.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View view1) {
                 DataBaseHelp dataBaseHelp = new DataBaseHelp(getContext(), "demo.db", null, 1);
                 dataBaseHelp.getWritableDatabase().execSQL("update demo set num=num+1 where product_id=" + product.getId());
                 TextView textView = (TextView) view.findViewById(R.id.number);
                 textView.setText(product.getNum() + 1 + "");
                 product.setNum(product.getNum() + 1);
             }
         });


         productLayout.delete.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View view1) {

                 final   DataBaseHelp dataBaseHelp=new DataBaseHelp(getContext(),"demo.db",null,1);
                 final ArrayList<Product> arrayList = new ArrayList<>();
                 if(product.getNum()>1) {
                     dataBaseHelp.getWritableDatabase().execSQL("update demo set num =num-1 where product_id=" + product.getId());
                     TextView textView = (TextView)view.findViewById(R.id.number);
                     textView.setText(product.getNum()-1+"");
                     product.setNum(product.getNum()-1);
                 }else if(product.getNum()==1) {
                     AlertDialog builder = new AlertDialog.Builder(getContext()).create();
                     builder.setMessage("确定删除?");
                     builder.setTitle("提示");

                     builder.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int which) {
                             return;
                         }
                     });

                     builder.setButton(DialogInterface.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialogInterface, int which) {
                             final Cursor cursor = dataBaseHelp.getWritableDatabase().query("demo",null,null,null,null,null,null);
                             while(cursor.moveToNext()){
                                 Product product=new Product();
                                 product.setId(cursor.getInt(1));
                                 product.setTitle(cursor.getString(2));
                                 product.setPrice(cursor.getString(3));
                                 product.setImage(cursor.getString(4));
                                 product.setNum(cursor.getInt(5));
                                 arrayList.add(product);
                             }
                             if(arrayList.get(position)!=null){
                                 dataBaseHelp.getWritableDatabase().execSQL("delete from demo where product_id="+arrayList.get(position).getId());
                                // final  BeanAdapter adapter = new BeanAdapter(this,R.layout.shoppingfinal,arrayList);
                               //  adapter.notifyDataSetChanged();
                                 arrayList.remove(position);
                                 System.out.println("success");
                             } else {
                                 System.out.println("failed");
                             }
                           // adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "删除列表项", Toast.LENGTH_SHORT).show();

                         }
                     });

                     builder.show();


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
        Button add;
        TextView textView;
        Button delete;
    }





}

