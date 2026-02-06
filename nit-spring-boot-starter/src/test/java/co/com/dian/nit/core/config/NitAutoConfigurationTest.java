package co.com.dian.nit.core.config;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.validation.DianNitValidator;
import co.com.dian.nit.core.service.DefaultNitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import jakarta.validation.ConstraintValidator;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NitAutoConfiguration Tests")
class NitAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(NitAutoConfiguration.class));

    @Test
    @DisplayName("Debe crear los beans básicos por defecto")
    void shouldCreateDefaultBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(NitValidator.class);
            assertThat(context).hasSingleBean(NitService.class);
            assertThat(context).hasSingleBean(ConstraintValidator.class);
        });
    }

    @Test
    @DisplayName("Debe permitir sobrescribir NitService")
    void shouldAllowNitServiceOverride() {
        contextRunner.withBean(NitService.class, () -> mock(NitService.class))
                .run(context -> {
                    assertThat(context).hasSingleBean(NitService.class);
                    assertThat(context).getBean(NitService.class).isNotInstanceOf(DefaultNitService.class);
                });
    }

    // Mock utility since Mockito.mock might not be in scope for withBean lambda easily
    private static NitService mock(Class<NitService> clazz) {
        return org.mockito.Mockito.mock(clazz);
    }
}
