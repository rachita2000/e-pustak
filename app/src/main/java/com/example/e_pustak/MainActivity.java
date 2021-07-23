package com.example.e_pustak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BooKInterfaceAdapater {
    BookAdapter mAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText btitle= findViewById(R.id.book_title);
        ImageButton bsearch=findViewById(R.id.search);
        progressBar=findViewById(R.id.progressbar);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter=new BookAdapter(MainActivity.this, this);
        recyclerView.setAdapter(mAdapter);
        bsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              progressBar.setVisibility(View.VISIBLE);
                if (btitle.getText().toString().isEmpty())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Please Enter the Title",Toast.LENGTH_LONG).show();
                    return;
                }
                fetchData(btitle.getText().toString());

            }
        });


    }

    public void fetchData(String title1){
        String url="https://www.googleapis.com/books/v1/volumes?q="+title1+"&filter=ebooks&key=AIzaSyAhRHN8lBH4JFSiflOAXnWdUfLfNdXI6HI";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray newJsonArray = response.getJSONArray("items");
                            ArrayList<Books> books = new ArrayList<>();
                           for (int i = 0; i < newJsonArray.length(); i++) {
                                JSONObject itemsObj = newJsonArray.getJSONObject(i);
                                JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                              String title="",publisher="",publishedDate="",description="",thumbnail="",previewLink="",infoLink="",buyLink="",currencyCode="",price="Rs. Free";
                               double amount = 0.0;
                               JSONArray authorsArray = new JSONArray();
                               JSONObject saleInfoObj=null; JSONObject retailPriceObj=null;
                               if (volumeObj.has("title")) {
                                   if(!volumeObj.isNull("title"))
                                   {
                                      title = volumeObj.getString("title");
                                   }
                               }
                               if (volumeObj.has("authors")) {
                                   if(!volumeObj.isNull("authors"))
                                   {
                                       authorsArray = volumeObj.optJSONArray("authors");
                                   }
                               }
                               if (volumeObj.has("publisher")) {
                                   if(!volumeObj.isNull("publisher"))
                                   {
                                        publisher = volumeObj.optString("publisher");
                                   }
                               }
                               if (volumeObj.has("publishedDate")&&volumeObj.getString("publishedDate")!=null) {

                                      publishedDate = volumeObj.optString("publishedDate");

                               }
                               if (volumeObj.has("description")) {
                                   if(!volumeObj.isNull("description"))
                                   {
                                        description = volumeObj.optString("description");
                                   }
                               }
                               if (volumeObj.has("imageLinks")) {
                                   if(!volumeObj.isNull("imageLinks"))
                                   {
                                       JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                                       assert imageLinks != null;
                                       if (imageLinks.has("thumbnail")) {
                                           if(!imageLinks.isNull("thumbnail"))
                                           {
                                               thumbnail = imageLinks.optString("thumbnail");
                                           }
                                       }
                                   }
                               }
                               if (volumeObj.has("previewLink")) {
                                   if(!volumeObj.isNull("previewLink"))
                                   {
                                      previewLink = volumeObj.optString("previewLink");
                                   }
                               }
                               if (volumeObj.has("infoLink")) {
                                   if(!volumeObj.isNull("infoLink"))
                                   {
                                        infoLink =volumeObj.optString("infoLink");
                                   }
                               }
                               if (itemsObj.has("saleInfo")) {
                                   if(!itemsObj.isNull("saleInfo"))
                                   {
                                      saleInfoObj = itemsObj.optJSONObject("saleInfo");
                                       assert saleInfoObj != null;
                                       if (saleInfoObj.has("buyLink")) {
                                           if(!saleInfoObj.isNull("buyLink"))
                                           {
                                              buyLink = saleInfoObj.optString("buyLink");
                                           }
                                       }
                                       if (saleInfoObj.has("retailPrice")) {
                                           if(!saleInfoObj.isNull("retailPrice"))
                                           {
                                               retailPriceObj =saleInfoObj.optJSONObject("retailPrice");
                                           }
                                           assert retailPriceObj != null;
                                           if (retailPriceObj.has("amount")) {
                                               if(!retailPriceObj.isNull("amount"))
                                               {
                                                   amount=retailPriceObj.optDouble("amount");
                                               }
                                           }
                                           if (retailPriceObj.has("currencyCode")) {
                                               if(!retailPriceObj.isNull("currencyCode"))
                                               {
                                                   currencyCode = retailPriceObj.optString("currencyCode");
                                               }
                                           }
                                           if(currencyCode.equals("INR"))
                                          price = "Rs. " +Double.toString(amount);
                                       }
                                   }
                               }
                                ArrayList<String> authorsArrayList = new ArrayList<>();
                               assert authorsArray != null;
                               if (authorsArray.length() != 0) {
                                    for (int j = 0; j < authorsArray.length(); j++)
                                        authorsArrayList.add(authorsArray.optString(j));
                                }

                                Books book=new Books(title,authorsArrayList,publisher,publishedDate,description,thumbnail,previewLink,infoLink,buyLink,price);
                                books.add(book);

//                               Log.d("MainAcitivity" ,title+" AU = "+authorsArrayList+" P= "+publisher+" D= "+publishedDate+" Tl= "+thumbnail +
//                                       " PL= "+previewLink+" IL= "+infoLink+" BL= "+buyLink+" price= "+price);
                            }
                           mAdapter.updateBook(books);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                           progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this,"No Book Found" ,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                   progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,"Something is wrong" ,Toast.LENGTH_LONG).show();

                    }
                });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(Books book) {
        Intent i = new Intent(MainActivity.this, Book_Details.class);
                i.putExtra("title", book.getTitle());
                i.putExtra("authors", book.getAuthors());
               i.putExtra("publisher", book.getPublisher());
                 i.putExtra("publishedDate", book.getPublishedDate());
                i.putExtra("description", book.getDescription());
                i.putExtra("thumbnail", book.getThumbnail());
                i.putExtra("previewLink", book.getPreviewLink());
               i.putExtra("infoLink", book.getInfoLink());
                i.putExtra("buyLink", book.getBuyLink());
              i.putExtra("price",book.getPrice());
                startActivity(i);
    }
}