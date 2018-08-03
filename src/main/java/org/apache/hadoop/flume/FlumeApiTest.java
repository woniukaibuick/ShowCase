package org.apache.hadoop.flume;

import org.apache.flume.Channel;
import org.apache.flume.Sink;
import org.apache.flume.Source;

/**
 * @ClassName:     FlumeApiTest.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * @author          gongva
 * @version         V1.0  
 * @Date           2018年8月3日 上午11:04:51 
 */
public class FlumeApiTest {

	public static void main(String[] args) {
		/**
		 * A source generates {@plainlink Event events} and calls methods on the
	    configured ChannelProcessor to persist those events into the configured channels. 
		Sources are associated with unique names that can be used for separating 
		configuration and working namespaces. 
		No guarantees are given regarding thread safe access. 
		 */
		Source flumeSourceOverrideInterface;
		/**
		A sink is connected to a Channel and consumes its contents, sending them to a configured 
		destination that may vary according to the sink type. 
		Sinks can be grouped together for various behaviors using SinkGroup and SinkProcessor. 
		They are polled periodically by a SinkRunner via the processor
		Sinks are associated with unique names that can be used for separating configuration and working namespaces. 
		While the Sink.process() call is guaranteed to only be accessed by a single thread, 
		other calls may be concurrently accessed and should thus be protected. 
		 */
		Sink flumeSinkOverrideInterface;
		/**
		A channel connects a Source to a Sink. The source acts as producer while the sink acts as a consumer of events.
		 The channel itself is the buffer between the two. 
		A channel exposes a Transaction interface that can be used by its clients to ensure atomic put and take semantics. 
		This is necessary to guarantee single hop reliability between agents. For instance, a source will successfully 
		produce an event if and only if that event can be committed to the source's associated channel. Similarly,
		 a sink will consume an event if and only if its respective endpoint can accept the event. The extent of transaction 
		 support varies for different channel implementations ranging from strong to best-effort semantics. 
		Channels are associated with unique names that can be used for separating configuration and working namespaces. 
		Channels must be thread safe, protecting any internal invariants as no guarantees are given as to when and by 
		how many sources/sinks they may be simultaneously accessed by. 
		 */
		Channel flumeChannelOverrideInterface;
		org.apache.flume.ChannelSelector cs;
		org.apache.flume.SinkProcessor sp;
		org.apache.flume.interceptor.Interceptor ii;
/*		org.apache.flume.channel.file.encryption.KeyProvider$Builder
		org.apache.flume.channel.file.encryption.CipherProvider
		org.apache.flume.serialization.EventSerializer$Builder */ 
		
	}
}
