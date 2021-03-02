package com.serex.appstorehb

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var productAdapter: ProductAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var productDao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
        var loggedUser = sharedPreferences.getBoolean(getString(R.string.logged_user_key), false)

        if(!loggedUser){
            startActivity(Intent(this, LoginActivity::class.java))
        }else{

            setContentView(R.layout.activity_main)

            productDao = AppDatabase.getInstance(this).productDao()
            var products = ArrayList<Product>(productDao.list())
            val staticProducts = ArrayList<Product>(getProducts())

            if(products.size !== staticProducts.size){
                products = ArrayList<Product>(getProducts())
                products.forEachIndexed { index, product -> productDao.insert(product) }
                Log.i("data","Inserted data")
            }else{
                Log.i("data","Data from database")
            }

            productAdapter = ProductAdapter(products)

            val recyclerView = this.findViewById<RecyclerView>(R.id.recyclerViewArticles)
            recyclerView.adapter = productAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)

        }

    }

    private fun getProducts(): List<Product> {

        val jsonFileString = getJsonDataFromAsset(applicationContext, "products.json")

        val gson = Gson()
        val listProductType = object : TypeToken<List<Product>>() {}.type

        var persons: List<Product> = gson.fromJson(jsonFileString, listProductType)
        return persons!!

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menuSettings -> {
                super.onOptionsItemSelected(item)
            }
            R.id.menuLogout -> {
                sharedPreferences.edit().putBoolean(getString(R.string.logged_user_key), false).apply()
                startActivity(Intent(this, LoginActivity::class.java))
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}