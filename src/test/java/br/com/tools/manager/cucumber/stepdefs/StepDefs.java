package br.com.tools.manager.cucumber.stepdefs;

import br.com.tools.manager.ManagerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ManagerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
