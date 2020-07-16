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
import org.springframework.kafka.core.KafkaOperations.OperationsCallback;
import org.springframework.kafka.core.KafkaOperations.ProducerCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

import per.cby.frame.common.util.JsonUtil;
import per.cby.frame.common.util.SpringContextUtil;
import per.cby.frame.kafka.annotation.KafkaProducer;

/**
 * Kafkf消息发送器
 * 
 * @author chenboyang
 *
 * @param <T> 消息类型
 */
@Deprecated
@SuppressWarnings("unchecked")
public interface KafkaJsonSender<T> {

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
    default KafkaTemplate<String, String> template() {
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
     * 发送默认topic消息
     * 
     * @param data 数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> sendDefault(T data) {
        return send(getTopic(), data);
    }

    /**
     * 发送默认topic消息
     * 
     * @param key  键
     * @param data 数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> sendDefault(String key, T data) {
        return send(getTopic(), key, data);
    }

    /**
     * 发送默认topic消息
     * 
     * @param partition 分区
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> sendDefault(Integer partition, String key, T data) {
        return send(getTopic(), partition, key, data);
    }

    /**
     * 发送默认topic消息
     * 
     * @param partition 分区
     * @param timestamp 时间戳
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> sendDefault(Integer partition, Long timestamp, String key,
            T data) {
        return send(getTopic(), partition, timestamp, key, data);
    }

    /**
     * 发送消息
     * 
     * @param topic 主题
     * @param data  数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(String topic, T data) {
        return template().send(topic, JsonUtil.toJson(data));
    }

    /**
     * 发送消息
     * 
     * @param topic 主题
     * @param key   键
     * @param data  数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(String topic, String key, T data) {
        return template().send(topic, key, JsonUtil.toJson(data));
    }

    /**
     * 发送消息
     * 
     * @param topic     主题
     * @param partition 分区
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(String topic, Integer partition, String key, T data) {
        return template().send(topic, partition, key, JsonUtil.toJson(data));
    }

    /**
     * 发送消息
     * 
     * @param topic     主题
     * @param partition 分区
     * @param timestamp 时间戳
     * @param key       键
     * @param data      数据
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(String topic, Integer partition, Long timestamp,
            String key, T data) {
        return template().send(topic, partition, timestamp, key, JsonUtil.toJson(data));
    }

    /**
     * 发送消息
     * 
     * @param record 消息记录
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(ProducerRecord<String, String> record) {
        return template().send(record);
    }

    /**
     * 发送消息
     * 
     * @param message 消息体
     * @return 发送结果监听
     */
    default ListenableFuture<SendResult<String, String>> send(Message<?> message) {
        return template().send(message);
    }

    /**
     * 获取分区信息列表
     * 
     * @param topic 主题
     * @return 分区信息列表
     */
    default List<PartitionInfo> partitionsFor(String topic) {
        return template().partitionsFor(topic);
    }

    /**
     * 获取矩阵信息
     * 
     * @return 矩阵信息
     */
    default Map<MetricName, ? extends Metric> metrics() {
        return template().metrics();
    }

    /**
     * 执行消息生产
     * 
     * @param <R>      返回类型
     * @param callback 消息生产回调
     * @return 执行结果
     */
    default <R> R execute(ProducerCallback<String, String, R> callback) {
        return template().execute(callback);
    }

    /**
     * 执行消息转换
     * 
     * @param <R>      返回类型
     * @param callback 消息转换回调
     * @return 执行结果
     */
    default <R> R executeInTransaction(OperationsCallback<String, String, R> callback) {
        return template().executeInTransaction(callback);
    }

    /**
     * 同步刷新
     */
    default void flush() {
        template().flush();
    }

    /**
     * 发送偏移转换
     * 
     * @param offsets 消息偏移
     */
    default void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets) {
        template().sendOffsetsToTransaction(offsets);
    }

    /**
     * 发送偏移转换
     * 
     * @param offsets         消息偏移
     * @param consumerGroupId 消费者分组编号
     */
    default void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId) {
        template().sendOffsetsToTransaction(offsets, consumerGroupId);
    }

}
