/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.demo.provider;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.demo.DemoService;

public class Application {

    private static final String REGISTRY_URL = "zookeeper://127.0.0.1:2181";

    public static void main(String[] args) {
        startWithBootstrap();
    }

    private static void startWithBootstrap() {
        // ServiceConfig实例, 泛型参数为业务接口实现类
        ServiceConfig<DemoServiceImpl> service = new ServiceConfig<>();
        // 指定业务接口
        service.setInterface(DemoService.class);
        // 指定实现类
        service.setRef(new DemoServiceImpl());

//        bootstrap单例对象
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        //生成一个 ApplicationConfig 的实例、指定ZK地址以及ServiceConfig实例
        bootstrap
                .application(new ApplicationConfig("dubbo-demo-api-provider"))
                .registry(new RegistryConfig(REGISTRY_URL))
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .service(service)
                .start()
                .await();
    }
}
