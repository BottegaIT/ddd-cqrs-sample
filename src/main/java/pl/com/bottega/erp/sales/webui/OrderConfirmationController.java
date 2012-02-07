/*
 * Copyright 2011-2012 the original author or authors.
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
package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

@Controller
@RequestMapping("/sales/confirmOrder")
public class OrderConfirmationController {

    @Inject
    private Gate gate;

    @Inject
    private OrderFinder orderFinder;

    @RequestMapping("/{orderId}")
    public String orderConfirmation(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("order", orderFinder.getClientOrderDetails(orderId));
        return "sales/orderConfirmation";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitOrder(@RequestParam("orderId") Long orderId) {
        gate.dispatch(new SubmitOrderCommand(orderId));
        return "redirect:/sales/confirmOrder/" + orderId;
    }
}
