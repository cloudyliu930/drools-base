package com.cloudy.capter13;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.Watch;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.PutResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EtcdUtil {

    public static String WATCH_KEY = "etcd_test100";
    //etcl客户端链接
    private static Client client = null;
    //链接初始化
    public static synchronized Client getEtclClient() {
        if(null == client){
            // client = Client.builder().endpoints("http://172.17.102.15:2379").build();
            client = Client.builder().endpoints("http://127.0.0.1:2379").build();
        }
        return client;
    }

    /**
     * 根据指定的配置名称获取对应的value
     * @param key 配置项
     * @return
     * @throws Exception
     */
    public static String getEtcdValueByKey(String key) throws Exception {
        List<KeyValue> kvs = EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(key)).get().getKvs();
        if(kvs.size()>0){
            String value = kvs.get(0).getValue().toStringUtf8();
            return value;
        }
        else {
            return null;
        }
    }

    /**
     * 根据指定的配置名称获取对应的value
     * @param key 配置项
     * @return
     * @throws Exception
     */
    public static byte[] getEtcdByteValueByKey(String key) throws Exception {
        List<KeyValue> kvs = EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(key)).get().getKvs();
        if(kvs.size()>0){
            byte[] value = kvs.get(0).getValue().getBytes();
            return value;
        }
        else {
            return null;
        }
    }

    /**
     * 新增或者修改指定的配置
     * @param key
     * @param value
     * @return
     */
    public static KeyValue putEtcdValueByKey(String key,String value) throws Exception{
        CompletableFuture<PutResponse> future = EtcdUtil.getEtclClient().getKVClient().put(ByteSequence.fromString(key),ByteSequence.fromBytes(value.getBytes("utf-8")));
        return future.get().getPrevKv();
    }

    /**
     * 新增或者修改指定的配置
     * @param key
     * @param value
     * @return
     */
    public static void putEtcdByteValueByKey(String key,byte[] value) throws Exception {
        CompletableFuture<PutResponse> future = EtcdUtil.getEtclClient().getKVClient().put(ByteSequence.fromString(key),ByteSequence.fromBytes(value));
        PutResponse response = future.get();
    }

    /**
     * 删除指定的配置
     * @param key
     * @return
     */
    public static void deleteEtcdValueByKey(String key){
         EtcdUtil.getEtclClient().getKVClient().delete(ByteSequence.fromString(key));

    }


    //V3 api配置初始化和监听
    public static void init() {
        try {
            //加载配置
            // getConfig(EtcdUtil.getEtclClient().getKVClient().get(ByteSequence.fromString(WATCH_KEY)).get().getKvs());
            //启动监听线程
            new Thread(() -> {
            //对某一个配置进行监听
                Watch.Watcher watcher = EtcdUtil.getEtclClient().getWatchClient().watch(ByteSequence.fromString(WATCH_KEY));
                try {
                    final int[] count = {0};
                    final long[] totalTime = {0};
                    while(true) {
                        watcher.listen().getEvents().stream().forEach(watchEvent -> {
                            KeyValue kv = watchEvent.getKeyValue();
                            //获取事件变化类型
                            // System.out.println(watchEvent.getEventType());
                            //获取发生变化的key
                            // System.out.println(kv.getKey().toStringUtf8());
                            //获取变化后的value
                            String afterChangeValue = kv.getValue().toStringUtf8();
                            // System.out.println(afterChangeValue);
                            System.out.println(count[0] + "receive end " + System.currentTimeMillis());
                            count[0]++;

                            totalTime[0] += System.currentTimeMillis();
                            System.out.println("receive total time:" + totalTime[0]);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static String getConfig(List<KeyValue> kvs){
        if(kvs.size()>0){
            String config = kvs.get(0).getValue().toStringUtf8();
            System.out.println("etcd 's config 's configValue is :"+config);
            return config;
        }
        else {
            return null;
        }
    }
}