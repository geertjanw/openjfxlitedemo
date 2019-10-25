package OpenJFXLiteDemo;

 import net.java.html.js.JavaScriptBody;
 import net.java.html.json.Models;
 
 public final class Demo {
     @JavaScriptBody(args = {}, javacall = true, body =
         "window.fxControllers = {};\n" +
         "window.fxControllers.create = function(name, ko, arr) {\n" +
         "  return @OpenJFXLiteDemo.Demo::createModel0(Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)(name, ko, arr); \n" +
         "};"
     )
     private static native void registerViewModels();
 
     static Object createModel0(String name, Object ko, Object[] arr) {
         Object fxModel = createModel(name, ko, arr);
         Object raw = Models.toRaw(fxModel);
         return raw;
     }
 
     private static Object createModel(String name, Object ko, Object[] arr) {
         switch (name) {
 //            case "ControllerViewModel": return new ControllerViewModel();
             default:
                 throw new IllegalArgumentException(name);
         }
     }
 
     public static void onPageLoad() {
         registerViewModels();
      }

}