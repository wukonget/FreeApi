package com.peng.freeapi.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 * 若把初始化内容放到initData实现
 * 就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 *
 * 注1:
 * 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 *
 * 注2:
 * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 * eg:
 * transaction.hide(aFragment);
 * transaction.show(aFragment);
 *
 * update 2017/01/23
 * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
 * 一般用于PagerAdapter需要同时刷新全部子Fragment的场景
 * 不要new 新的 PagerAdapter 而采取reset数据的方式
 * 所以要求Fragment重新走initData方法
 * 故使用 [BaseFragment.setForceLoad]来让Fragment下次执行initData
 *
 * Created by Mumu
 * on 2015/11/2.
</pre> *
 */
abstract class BaseLazyFragment : Fragment() {
    /**
     * Fragment title
     */
    var fragmentTitle: String? = null
    /**
     * 是否可见状态 为了避免和[Fragment.isVisible]冲突 换个名字
     */
    var isFragmentVisible: Boolean = false
        private set
    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    var isPrepared: Boolean = false
        private set
    /**
     * 是否第一次加载
     */
    var isFirstLoad = true
        private set
    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * 故使用 [BaseFragment.setForceLoad]来让Fragment下次执行initData
    </pre> *
     */
    private var forceLoad = false

    var title: String
        get() {
            if (null == fragmentTitle) {
                setDefaultFragmentTitle(null)
            }
            return if (TextUtils.isEmpty(fragmentTitle)) "" else fragmentTitle.toString()
        }
        set(title) {
            fragmentTitle = title
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        // 导致initData反复执行,所以这里注释掉
        // isFirstLoad = true;

        // 2016/04/29
        // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
        // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        isFirstLoad = true
        val view = initViews(inflater, container, savedInstanceState)
        isPrepared = true
        lazyLoad()
        return view
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    protected fun onVisible() {
        isFragmentVisible = true
        lazyLoad()
    }

    protected fun onInvisible() {
        isFragmentVisible = false
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected fun lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (forceLoad || isFirstLoad) {
                forceLoad = false
                isFirstLoad = false
                initData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
    }

    /**
     * 被ViewPager移出的Fragment 下次显示时会从getArguments()中重新获取数据
     * 所以若需要刷新被移除Fragment内的数据需要重新put数据 eg:
     * Bundle args = getArguments();
     * if (args != null) {
     * args.putParcelable(KEY, info);
     * }
     */
    fun initVariables(bundle: Bundle) {}

    protected abstract fun initViews(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun initData()

    /**
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     */
    fun setForceLoad(forceLoad: Boolean) {
        this.forceLoad = forceLoad
    }

    /**
     * 设置fragment的Title直接调用 [BaseFragment.setTitle],若不显示该title 可以不做处理
     *
     * @param title 一般用于显示在TabLayout的标题
     */
    protected abstract fun setDefaultFragmentTitle(title: String?)
}
