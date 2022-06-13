package com.neppplus.gudocin_android

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {
  /**
   * AtomicBoolean: Multi-Thread 환경에서 동시성 보장
   */
  private val mPending = AtomicBoolean(false)

  /**
   * View(LifeCycleOwner) 활성화 상태가 되거나
   * setValue 통해 값이 바뀌었을 때 호출되는 함수
   */
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    if (hasActiveObservers()) {
      Log.d(TAG, "Multiple observers registered but only one will be notified of changes")
    }

    /**
     * CompareAndSet: mPending 변수가 true 일 시 if문 내 로직 처리 후 false 업데이트
     * setValue 통해서만 mPending 값이 바뀌기 때문에 Configuration Changed 가 일어나도 데이터를 전달하지 않음
     */
    // Observer the internal MutableLiveData
    super.observe(owner, Observer { t ->
      if (mPending.compareAndSet(true, false)) {
        observer.onChanged(t)
      }
    })
  }

  /**
   * LiveData 로써 들고 있는 데이터 값을 변경하는 함수
   * mPending(AtomicBoolean) 변수를 true 로 바꾸어 observe 함수 내 if문 처리 가능
   */
  @MainThread
  override fun setValue(value: T?) {
    mPending.set(true)
    super.setValue(value)
  }

  @MainThread
  fun call() {
    value = null
  }

  companion object {
    private const val TAG = "SingleLiveEvent"
  }
}