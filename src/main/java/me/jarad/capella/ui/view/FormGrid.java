package me.jarad.capella.ui.view;import com.vaadin.shared.MouseEventDetails;import com.vaadin.shared.ui.ValueChangeMode;import com.vaadin.ui.Grid;import com.vaadin.ui.HorizontalLayout;import com.vaadin.ui.TextField;import java.lang.reflect.ParameterizedType;import java.lang.reflect.Type;/** * @author vyarmole on 10.04.17. */public class FormGrid<T> extends Grid<T> {  private Class contentClass;  private HorizontalLayout actions;  private String id;  public FormGrid() {    Type superClass = getClass().getGenericSuperclass();    if (superClass instanceof Class<?>) {      throw new IllegalArgumentException("Internal error");    }    Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];    contentClass = type.getClass();    id = contentClass.getSimpleName();    generateActions();  }  protected void init() {    setColumns("id", "name", "pwd");    setHeight(100, Unit.PERCENTAGE);    setWidth(100, Unit.PERCENTAGE);    setSelectionMode(Grid.SelectionMode.SINGLE);    addItemClickListener(event -> {      MouseEventDetails mouseEventDetails = event.getMouseEventDetails();      if (mouseEventDetails.isDoubleClick()) {        T item = event.getItem();        showItem();      }    });  }  private void showItem() {    //Window userWindowView = new ItemViewWindow<T>();    //UI.getCurrent().addWindow(userWindowView);  }  protected void generateActions() {    TextField filter = new TextField();    filter.setPlaceholder("Filter ... ");    filter.setValueChangeMode(ValueChangeMode.LAZY);    filter.addValueChangeListener(e -> filteringProcess(e.getValue()));    actions = new HorizontalLayout(filter);    actions.addComponents(FormGridButtons.getGridButtons());    filteringProcess(null);   }  void filteringProcess(String filterText) {   /* if (StringUtils.isEmpty(filterText)) {      this.setItems(userService.getUsers());    } else {      this.setItems(userService.getUserByName(filterText));    }  */  }}