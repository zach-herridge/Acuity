package com.acuitybotting.website.dashboard.views.resources.accounts;

import com.acuitybotting.db.arango.acuity.rabbit_db.domain.sub_documents.RsAccountInfo;
import com.acuitybotting.website.dashboard.components.general.list_display.InteractiveList;
import com.acuitybotting.website.dashboard.services.AccountsService;
import com.acuitybotting.website.dashboard.utils.Components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

@SpringComponent
@UIScope
public class AccountListComponent extends InteractiveList<RsAccountInfo> {

    @Autowired
    public AccountListComponent(AccountsService accountsService) {
        getControls().add(Components.button(VaadinIcon.PLUS_CIRCLE,  event -> getUI().ifPresent(ui -> ui.navigate(AccountEditView.class))));

        withColumn("Email", "30%", document -> {
            Div div = new Div();
            div.getElement().addEventListener("click", domEvent -> getUI().ifPresent(ui -> ui.navigate(AccountView.class, document.getSubKey())));
            return div;
        }, (document, div) -> div.setText(document.getSubKey()));
        withColumn("Last World", "10%", document -> new Div(), (document, div) -> div.setText(String.valueOf(document.getWorld())));
        withColumn("Password", "10%", document -> new Div(), (document, div) -> div.setText(document.getEncryptedPassword() != null ? "Set" : "None"));
        withColumn("", "5%", "5%", rsAccountInfo -> Components.button(VaadinIcon.EDIT, event -> getUI().ifPresent(ui -> ui.navigate(AccountEditView.class, rsAccountInfo.getSubKey()))), (rsAccountInfo, button) -> {});

        withSearchable(RsAccountInfo::getSubKey);
        withLoad(RsAccountInfo::getSubKey, accountsService::loadAccounts);
    }
}