/* Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.job;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.snaker.engine.SnakerEngine;
import org.snaker.engine.core.ServiceContext;
import org.snaker.engine.helper.AssertHelper;

/**
 * 抽象的job类
 * @author yuqs
 * @since 1.4
 */
public abstract class AbstractJob implements Job {
	public static final String KEY = "ID";
	protected SnakerEngine engine = ServiceContext.getContext().getEngine();
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		if(engine == null) throw new JobExecutionException("Snaker流程引擎初始化失败.");
		Map<String, Object> data = context.getJobDetail().getJobDataMap().getWrappedMap();
		if(data == null) throw new JobExecutionException("根据job执行的上下文获取不到snaker相关信息.");
		String id = (String)data.get(KEY);
		AssertHelper.notEmpty(id);
		data.remove(KEY);
		exec(id, data);
	}

	/**
	 * 抽象方法，由具体的job进行处理
	 * @param id 标识id
	 * @param data 执行数据
	 * @throws JobExecutionException job执行异常
	 */
	abstract void exec(String id, Map<String, Object> data) throws JobExecutionException;
}