/**
 * Copyright (c) 2011-2013, dafei 李飞 (myaniu AT gmail DOT com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.ext.plugin.shiro;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;

public class ShiroInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        AuthzHandler ah = ShiroKit.getAuthzHandler(inv.getActionKey());
        // 存在访问控制处理器。
        if (ah != null) {
            try {
                // 执行权限检查。
                ah.assertAuthorized();
            } catch (UnauthenticatedException lae) {
                // RequiresGuest，RequiresAuthentication，RequiresUser，未满足时，抛出未经授权的异常。
                // 如果没有进行身份验证，返回HTTP401状态码
                inv.getController().renderError(401);
                return;
            } catch (AuthorizationException ae) {
                // RequiresRoles，RequiresPermissions授权异常
                // 如果没有权限访问对应的资源，返回HTTP状态码403。
                inv.getController().renderError(403);
                return;
            } catch (Exception e) {
                // 出现了异常，应该是没有登录。
                inv.getController().renderError(401);
                return;
            }
        }
        // 执行正常逻辑
        inv.invoke();
    }
}
