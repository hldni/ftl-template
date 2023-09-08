package ${packageName}.controller;

import ${packageName}.domain.${className};
import ${packageName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* ${tableRemarks}
*/
@RestController
@RequestMapping("/${classNameLower}")
public class ${className}Controller {
    @Autowired
    private ${className}Service ${classNameLower}Service;
}