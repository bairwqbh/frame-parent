package per.cby.frame.kafka;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.kafka.annotation.KafkaProducer;

/**
 * Kafkf消息发送器
 * 
 * @author chenboyang
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
@SuppressWarnings("unchecked")
public interface KafkaSender<K, V> extends KafkaOperations<K, V> {

    /**
     * 获取通道信息
     * 
     * @return 信息
     */
    default KafkaProducer channelInfo() {
        return getClass().getAnnotation(KafkaProducer.class);
    }

    /**
     * 获取发送模板
     * 
     * @return 模板
     */
    default KafkaTemplate<K, V> template() {
        if (channelInfo() != null && StringUtils.isNotBlank(channelInfo().template())) {
            return SpringContextUtil.getBean(channelInfo().template());
        }
        return SpringContextUtil.getBean(KafkaTemplate.class);
    }

    /**
     * 获取topic
     * 
     * @return topic
     */
    default String getTopic() {
        if (channelInfo() != null && StringUtils.isNotBlank(channelInfo().topic())) {
            return channelInfo().topic();
        }
        return template().getDefaultTopic();
    }

    /**
     * 发送消息
     * 
     * @param data 数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<K, V>> sendMsg(V data) {
        return send(getTopic(), data);
    }

    /**
     * 发送消息
     * 
     * @param key  键
     * @param data 数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<K, V>> sendMsg(K key, V data) {
        return send(getTopic(), key, data);
    }

    /**
     * 发送消息
     * 
     * @param partition 部分
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<K, V>> sendMsg(Integer partition, K key, V data) {
        return send(getTopic(), partition, key, data);
    }

    /**
     * 发送消息
     * 
     * @param partition 部分
     * @param timestamp 时间戳
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<K, V>> sendMsg(Integer partition, Long timestamp, K key, V data) {
        return send(getTopic(), partition, timestamp, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> sendDefault(V data) {
        return sendMsg(data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> sendDefault(K key, V data) {
        return sendMsg(key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, K key, V data) {
        return sendMsg(partition, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, Long timestamp, K key, V data) {
        return sendMsg(partition, timestamp, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(String topic, V data) {
        return template().send(topic, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(String topic, K key, V data) {
        return template().send(topic, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, K key, V data) {
        return template().send(topic, partition, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, Long timestamp, K key, V data) {
        return template().send(topic, partition, timestamp, key, data);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record) {
        return template().send(record);
    }

    @Override
    default ListenableFuture<SendResult<K, V>> send(Message<?> message) {
        return template().send(message);
    }

    @Override
    default List<PartitionInfo> partitionsFor(String topic) {
        return template().partitionsFor(topic);
    }

    @Override
    default Map<MetricName, ? extends Metric> metrics() {
        return template().metrics();
    }

    @Override
    default <T> T execute(ProducerCallback<K, V, T> callback) {
        return template().execute(callback);
    }

    @Override
    default <T> T executeInTransaction(OperationsCallback<K, V, T> callback) {
        return template().executeInTransaction(callback);
    }

    @Override
    default void flush() {
        template().flush();
    }

    @Override
    default void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets) {
        template().sendOffsetsToTransaction(offsets);
    }

    @Override
    default void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId) {
        template().sendOffsetsToTransaction(offsets, consumerGroupId);
    }

}
