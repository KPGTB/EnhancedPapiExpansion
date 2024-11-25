package eu.kpgtb.util.placeholder.parameter;

import eu.kpgtb.util.placeholder.parameter.bukkit.OfflinePlayerConverter;
import eu.kpgtb.util.placeholder.parameter.bukkit.PlayerConverter;
import eu.kpgtb.util.placeholder.parameter.bukkit.WorldConverter;
import eu.kpgtb.util.placeholder.parameter.java.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class ParameterHandler {
    private final Map<Class<?>, IParameterConverter<?>> converters;

    private ParameterHandler() {
        this.converters = new HashMap<>();

        registerParser(
                new OfflinePlayerConverter(),
                new PlayerConverter(),
                new WorldConverter(),
                new BooleanConverter(),
                new ByteConverter(),
                new DoubleConverter(),
                new FloatConverter(),
                new IntegerConverter(),
                new LongConverter(),
                new ShortConverter(),
                new StringConverter()
        );
    }

    /**
     * Register parameter converter
     * @param converters Converter instances
     */
    @SuppressWarnings("unchecked")
    public void registerParser(IParameterConverter<?>... converters) {
        for (IParameterConverter<?> converter : converters) {
            ParameterizedType type = (ParameterizedType) converter.getClass().getGenericInterfaces()[0];
            Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];

            this.converters.put(clazz,converter);
            if (!clazz.isPrimitive()) {
                Class<?> primitiveClazz = clazz.equals(Boolean.class) ? Boolean.TYPE :
                    clazz.equals(Character.class) ? Character.TYPE :
                    clazz.equals(Byte.class) ? Byte.TYPE :
                    clazz.equals(Short.class) ? Short.TYPE :
                    clazz.equals(Integer.class) ? Integer.TYPE :
                    clazz.equals(Long.class) ? Long.TYPE :
                    clazz.equals(Float.class) ? Float.TYPE :
                    clazz.equals(Double.class) ? Double.TYPE :
                    null;
                if (primitiveClazz != null) {
                    this.converters.put(primitiveClazz, converter);
                }
            }
        }
    }

    /**
     * Get converter of specified class
     * @param clazz Class that have to be converted
     * @return IParameterConverter or null if there isn't any converters of this class
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> IParameterConverter<T> getConverter(Class<T> clazz) {
        return (IParameterConverter<T>) this.converters.get(clazz);
    }

    /**
     * Check if string can be covert to class
     * @param s String that you want to convert
     * @param expected Class that is expected
     * @return true if you can convert, or false if you can't
     */
    @SuppressWarnings("unchecked")
    public <T, Z extends Enum<Z>> boolean canConvert(String s, Class<T> expected, JavaPlugin plugin) {
        if(expected.isEnum()) {
            Class<Z> enumClass = (Class<Z>) expected;
            EnumConverter<Z> enumConverter = new EnumConverter<>(enumClass);
            return enumConverter.canConvert(s);
        }
        IParameterConverter<T> converter = getConverter(expected);
        if(converter == null) {
            return false;
        }
        return converter.canConvert(s, plugin);
    }

    /**
     * Convert string to class
     * @param s String that you want to convert
     * @param expected Class that is expected
     * @return Class that is converted from string
     */
    @SuppressWarnings("unchecked")
    public <T, Z extends Enum<Z>> T convert(String s, Class<T> expected, JavaPlugin plugin) {
        if(expected.isEnum()) {
            Class<Z> enumClass = (Class<Z>) expected;
            EnumConverter<Z> enumConverter = new EnumConverter<>(enumClass);
            return (T) enumConverter.convert(s);
        }
        IParameterConverter<T> converter = getConverter(expected);
        if(!canConvert(s, expected, plugin) || converter == null) {
            throw new IllegalArgumentException("You try convert string to class that you can't convert");
        }
        return converter.convert(s, plugin);
    }


    private static ParameterHandler instance;
    public static synchronized ParameterHandler getInstance() {
        if(instance == null) {
            instance = new ParameterHandler();
        }
        return instance;
    }
}
