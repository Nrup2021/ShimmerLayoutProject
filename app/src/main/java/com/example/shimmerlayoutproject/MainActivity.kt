package com.example.shimmerlayoutproject

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shimmerlayoutproject.databinding.ActivityMainBinding
import com.example.shimmerlayoutproject.utils.showProgressAlertDialog

// Reference :  https://medium.com/mobile-app-development-publication/swipe-to-refresh-not-showing-why-96b76c5c93e7
// https://www.geeksforgeeks.org/pull-to-refresh-with-recyclerview-in-android-with-example/

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productList: ArrayList<Product>
    private lateinit var adapter: ProductAdapter
    private var myProgressBarDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = ArrayList()
        setUpRecyclerViewWithData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            setUpRecyclerViewWithData()
        }
    }

    private fun setUpRecyclerViewWithData() {
        showSwipeToRefreshLayout()
        showLoader()
        Handler(Looper.getMainLooper()).postDelayed({
            productList = getProductList()
            binding.apply {
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = ProductAdapter((productList))
                recyclerView.adapter = adapter
                hideSwipeToRefreshLayout()
                hideLoader()
                adapter.setOnItemClickListener { product ->
                    Toast.makeText(this@MainActivity, "${product.name} clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }, 3000)

    }

    // Call when the a network service is invoke. We should disable
    // further swipe-to-refresh since we don't want a double network
    // call
    private fun showSwipeToRefreshLayout() {
        binding.apply {
            if (!swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.post {
                    swipeRefreshLayout.isEnabled = false
                    swipeRefreshLayout.isRefreshing = true
                }
            }
        }
        Log.e("SwipeRefresh", "Started...")
    }

    // Call when the a network service is done. We should re-enable
    // swipe-to-refresh as now we allow user to refresh it.
    private fun hideSwipeToRefreshLayout() {
        binding.apply {
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.post {
                    swipeRefreshLayout.isRefreshing = false
                    swipeRefreshLayout.isEnabled = true
                }
            }
        }
        Log.e("SwipeRefresh", "Stopped...")
    }

    //  showing dialog
    private fun showLoader() {
        if (myProgressBarDialog == null)
            myProgressBarDialog = showProgressAlertDialog {
                cancelable = false
                isBackGroundTransparent = true
            }
        myProgressBarDialog?.show()
    }

    private fun hideLoader() {
        if (myProgressBarDialog != null) {
            myProgressBarDialog!!.dismiss()
        }
    }

    private fun getProductList(): ArrayList<Product> {
        productList = mutableListOf(
            Product(
                1,
                "Camera",
                "https://picsum.photos/200",
                "Sony camera with high definition",
                4999.0f
            ),
            Product(
                2,
                "Laptop",
                "https://picsum.photos/200",
                "ASUS Vivobook 15, 15.6-inch (39.62 cms) FHD",
                40999.0f
            ),
            Product(
                3,
                "Shirt",
                "https://picsum.photos/200",
                "Diverse Men's Regular Formal Shirt",
                469.0f
            ),
            Product(
                4,
                "Book",
                "https://picsum.photos/200",
                "Oxford Student Atlas for India, Fourth Edition",
                150.0f
            ),
            Product(
                5,
                "Mobile",
                "https://picsum.photos/200",
                "MI (Midnight Black, 4GB, 64GB Storage) | 6000mAh Battery",
                9999.0f
            ),
            Product(
                6,
                "Camera",
                "https://picsum.photos/200",
                "Cannon camera with high definition",
                4999.0f
            ),
            Product(
                7,
                "Laptop",
                "https://picsum.photos/200",
                "Vivobook 15, 15.6-inch (39.62 cms) FHD",
                40999.0f
            ),
            Product(
                8,
                "Shirt",
                "https://picsum.photos/200",
                "Men's Regular Formal Shirt",
                399.0f
            ),
            Product(
                9,
                "Book",
                "https://picsum.photos/200",
                "Atlas for India, Fourth Edition",
                114.0f
            ),
            Product(
                10,
                "Mobile",
                "https://picsum.photos/200",
                "Samsung Galaxy M13 (Midnight Blue, 4GB, 64GB Storage) | 6000mAh Battery",
                11999.0f
            ),
            Product(
                11,
                "Camera",
                "https://picsum.photos/200",
                "Coolpix camera with high definition",
                3999.0f
            ),
            Product(
                12,
                "Laptop",
                "https://picsum.photos/200",
                "Dell 15, 15.6-inch (39.62 cms) FHD",
                32999.0f
            ),
            Product(
                13,
                "Shirt",
                "https://picsum.photos/200",
                "Women's Regular Formal Shirt",
                299.0f
            ),
            Product(
                14,
                "Book",
                "https://picsum.photos/200",
                "IIT Student Atlas for India, Fourth Edition",
                225.0f
            ),
            Product(
                15,
                "Mobile",
                "https://picsum.photos/200",
                "Oppo (Midnight Blue, 4GB, 64GB Storage) | 6000mAh Battery",
                8999.0f
            ),

            ) as ArrayList<Product>

        return productList
    }

}