package com.peng.freeapi.activitys

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import android.view.MenuItem
import cn.bertsir.zbar.QrConfig
import cn.bertsir.zbar.QrManager
import com.peng.freeapi.R
import com.peng.freeapi.fragments.AndroidFragment
import com.peng.freeapi.fragments.ImageListFragment
import com.peng.freeapi.fragments.NameListFragment
import com.peng.freeapi.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 首页
 */

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        collapsingToolbarLayout.title = getString(R.string.app_name)
        collapsingToolbarLayout.setExpandedTitleColor(resources.getColor(R.color.colorAccent))

        setViewPagerAndTab()

        navigationView.setNavigationItemSelectedListener(this@MainActivity)

    }

    private fun setViewPagerAndTab() {

        val nameListFragment = NameListFragment()
        val imageListFragment = ImageListFragment()
        val androidFragment = AndroidFragment()

        val titles = ArrayList<String>()
        val pagerList = ArrayList<Fragment>()

        titles.add("个性网名")
        titles.add("美图欣赏")
        titles.add("Android")
        pagerList.add(nameListFragment)
        pagerList.add(imageListFragment)
        pagerList.add(androidFragment)

        viewpager.adapter = ViewPagerAdapter(titles, pagerList, supportFragmentManager)

        tabLayout.setupWithViewPager(viewpager,true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                toScan()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.toJoke -> {
                CommonUtil.showToast("去段子页面")
                true
            }
            else -> false
        }


    }

    /**
     * 去扫码界面
     */
    private fun toScan() {
        val qrConfig = QrConfig.Builder()
                .setDesText("将二维码放置在框中\r\n或从相册选择图片识别")//扫描框下文字
                .setShowDes(true)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(resources.getColor(R.color.colorPrimary))//设置扫描框颜色
                .setLineColor(resources.getColor(R.color.colorAccent))//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_ALL)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
//                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(false)//是否扫描成功后bi~的声音
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码")//设置Tilte文字
                .setTitleBackgroudColor(Color.TRANSPARENT)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create()
        QrManager.getInstance().init(qrConfig).startScan(this@MainActivity) { result -> CommonUtil.showToast(result)}
    }


    class ViewPagerAdapter(private var titles: ArrayList<String> = ArrayList<String>(), private var pagerList: ArrayList<Fragment> = ArrayList<Fragment>(), fm:FragmentManager) : FragmentPagerAdapter (fm){

        override fun getItem(position: Int): Fragment {
            return pagerList[position]
        }

        override fun getCount(): Int {
            return pagerList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
