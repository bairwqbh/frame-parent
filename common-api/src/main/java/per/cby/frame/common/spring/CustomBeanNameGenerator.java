package per.cby.frame.common.spring;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * SpringBean注解命名生成器
 * 
 * @author chenboyang
 *
 */
public class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {

    /** SpringBean注解类名称 */
    private final String SPRINGBEAN_ANNOTATION_CLASSNAME = "per.cby.frame.common.spring.SpringBean";

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            AnnotationMetadata amd = ((AnnotatedBeanDefinition) definition).getMetadata();
            Set<String> types = amd.getAnnotationTypes();
            String beanName = null;
            for (String type : types) {
                AnnotationAttributes attributes = AnnotationAttributes
                        .fromMap(amd.getAnnotationAttributes(type, false));
                if (isSpringBeanValue(type, amd.getMetaAnnotationTypes(type), attributes)) {
                    Object value = attributes.get("value");
                    if (value instanceof String) {
                        String strVal = (String) value;
                        if (StringUtils.hasLength(strVal)) {
                            if (beanName != null && !strVal.equals(beanName)) {
                                throw new IllegalStateException("Stereotype annotations suggest inconsistent "
                                        + "component names: '" + beanName + "' versus '" + strVal + "'");
                            }
                            beanName = strVal;
                        }
                    }
                }
            }
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        return super.generateBeanName(definition, registry);
    }

    /**
     * 是否包含SpringBean注解命名属性
     * 
     * @param annotationType      注解类型
     * @param metaAnnotationTypes 注解类型集
     * @param attributes          注解属性
     * @return 是否包含
     */
    protected boolean isSpringBeanValue(String annotationType, Set<String> metaAnnotationTypes,
            Map<String, Object> attributes) {
        boolean isStereotype = SPRINGBEAN_ANNOTATION_CLASSNAME.equals(annotationType)
                || (metaAnnotationTypes != null && metaAnnotationTypes.contains(SPRINGBEAN_ANNOTATION_CLASSNAME))
                || "javax.annotation.ManagedBean".equals(annotationType) || "javax.inject.Named".equals(annotationType);
        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }

}
