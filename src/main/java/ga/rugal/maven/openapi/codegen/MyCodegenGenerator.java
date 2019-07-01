package ga.rugal.maven.openapi.codegen;

import java.io.File;
import java.util.*;

import org.openapitools.codegen.*;
import org.openapitools.codegen.languages.AbstractJavaCodegen;

public class MyCodegenGenerator extends AbstractJavaCodegen {

  protected String apiVersion = "1.0.0";

  /**
   * Configures the type of generator.
   *
   * @return the CodegenType for this generator
   *
   * @see org.openapitools.codegen.CodegenType
   */
  @Override
  public CodegenType getTag() {
    return CodegenType.CLIENT;
  }

  /**
   * Configures a friendly name for the generator. This will be used by the generator to select the
   * library with the -g flag.
   *
   * @return the friendly name for the generator
   */
  @Override
  public String getName() {
    return "spring-boot-feign";
  }

  /**
   * Provides an opportunity to inspect and modify operation data before the code is generated.
   *
   * @param objs
   * @param allModels
   *
   * @return
   */
  @Override
  public Map<String, Object> postProcessOperationsWithModels(final Map<String, Object> objs,
                                                             final List<Object> allModels) {

    // to try debugging your code generator:
    // set a break point on the next line.
    // then debug the JUnit test called LaunchGeneratorInDebugger
    final Map<String, Object> results = super.postProcessOperationsWithModels(objs, allModels);

    final Map<String, Object> ops = (Map<String, Object>) results.get("operations");
    final ArrayList<CodegenOperation> opList = (ArrayList<CodegenOperation>) ops.get("operation");

    // iterate over the operation and perhaps modify something
    for (final CodegenOperation co : opList) {
      // example:
      // co.httpMethod = co.httpMethod.toLowerCase();
    }

    return results;
  }

  /**
   * Returns human-friendly help for the generator. Provide the consumer with help tips, parameters
   * here
   *
   * @return A string value for the help message
   */
  @Override
  public String getHelp() {
    return "Generates a my-codegen client library.";
  }

  public MyCodegenGenerator() {
    super();

    // set the output folder here
    this.outputFolder = "generated-code/spring-boot-feign";

    /**
     * Models. You can write model files using the modelTemplateFiles map. if you want to create one
     * template for file, you can do so here. for multiple files for model, just put another entry
     * in the `modelTemplateFiles` with a different extension
     */
    this.modelTemplateFiles.put(
      "model.mustache", // the template to use
      ".java");       // the extension for each file to write

    /**
     * Api classes. You can write classes for each Api file with the apiTemplateFiles map. as with
     * models, add multiple entries with different extensions for multiple files per class
     */
    this.apiTemplateFiles.put(
      "api.mustache", // the template to use
      ".java");       // the extension for each file to write

    /**
     * Template Location. This is the location which templates will be read from. The generator will
     * use the resource stream to attempt to read the templates.
     */
    this.templateDir = "spring-boot-feign";

    /**
     * Api Package. Optional, if needed, this can be used in templates
     */
    this.apiPackage = "org.openapitools.api";

    /**
     * Model Package. Optional, if needed, this can be used in templates
     */
    this.modelPackage = "org.openapitools.model";

    /**
     * Reserved words. Override this with reserved words specific to your language
     */
    this.reservedWords = new HashSet<>(
      Arrays.asList(
        "sample1", // replace with static values
        "sample2")
    );

    /**
     * Additional Properties. These values can be passed to the templates and are available in
     * models, apis, and supporting files
     */
    this.additionalProperties.put("apiVersion", apiVersion);

    /**
     * Supporting Files. You can write single files for the generator with the entire object tree
     * available. If the input file has a suffix of `.mustache it will be processed by the template
     * engine. Otherwise, it will be copied
     */
    this.supportingFiles.add(new SupportingFile("myFile.mustache", // the input template or file
                                                "", // the destination folder, relative `outputFolder`
                                                "myFile.java") // the output file
    );

    /**
     * Language Specific Primitives. These types will not trigger imports by the client generator
     */
    this.languageSpecificPrimitives = new HashSet<>(
      Arrays.asList(
        "Type1", // replace these with your types
        "Type2")
    );
  }

  /**
   * Escapes a reserved word as defined in the `reservedWords` array.Handle escaping those terms
   * here. This logic is only called if a variable matches the reserved words
   *
   * @param name
   *
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(final String name) {
    return "_" + name;  // add an underscore to the name
  }

  /**
   * Location to write model files.You can use the modelPackage() as defined when the class is
   * instantiated
   *
   * @return
   */
  @Override
  public String modelFileFolder() {
    return String.format("%s/%s/%s", this.outputFolder,
                         this.sourceFolder,
                         modelPackage().replace('.', File.separatorChar));
  }

  /**
   * Location to write api files.You can use the apiPackage() as defined when the class is
   * instantiated
   *
   * @return
   */
  @Override
  public String apiFileFolder() {
    return String.format("%s/%s/%s", this.outputFolder,
                         this.sourceFolder,
                         apiPackage().replace('.', File.separatorChar));
  }

  /**
   * override with any special text escaping logic to handle unsafe characters so as to avoid code
   * injection
   *
   * @param input String to be cleaned up
   *
   * @return string with unsafe characters removed or escaped
   */
  @Override
  public String escapeUnsafeCharacters(final String input) {
    //TODO: check that this logic is safe to escape unsafe characters to avoid code injection
    return input;
  }

  /**
   * Escape single and/or double quote to avoid code injection
   *
   * @param input String to be cleaned up
   *
   * @return string with quotation mark removed or escaped
   */
  @Override
  public String escapeQuotationMark(final String input) {
    //TODO: check that this logic is safe to escape quotation mark to avoid code injection
    return input.replace("\"", "\\\"");
  }
}
