package eu.kpgtb.util.placeholder;

import eu.kpgtb.util.placeholder.annotation.NotPlaceholder;
import eu.kpgtb.util.placeholder.annotation.Placeholder;
import eu.kpgtb.util.placeholder.annotation.PlaceholderParam;
import eu.kpgtb.util.placeholder.annotation.RelationalPlaceholder;
import eu.kpgtb.util.placeholder.parameter.ParameterHandler;
import eu.kpgtb.util.placeholder.parameter.PlaceholderParameter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class EnhancedPapiExpansion extends PlaceholderExpansion implements Relational {

    private final String identifier;
    private final JavaPlugin plugin;
    private final Map<String, List<PlaceholderInfo>> placeholders;

    private String author;
    private String version;
    private boolean persist;
    private boolean canRegister;

    public EnhancedPapiExpansion(String identifier, JavaPlugin plugin) {
        this.identifier = identifier;
        this.plugin = plugin;
        this.placeholders = new HashMap<>();

        PluginDescriptionFile description = plugin.getDescription();
        this.author = description.getAuthors().isEmpty() ? "EnhancedPapiExpansion" : description.getAuthors().get(0);
        this.version = description.getVersion();

        scanClass();
    }

    private void scanClass() {
        for (Method method : getClass().getDeclaredMethods()) {
            if(method.getDeclaredAnnotation(NotPlaceholder.class) != null) continue;

            String prefix = StringUtil.camelToSnake(method.getName());
            String description = "";
            boolean relational = false;

            Placeholder placeholderAnn = method.getDeclaredAnnotation(Placeholder.class);
            if(placeholderAnn != null) {
                description = placeholderAnn.description();
                if(!placeholderAnn.value().isEmpty()) prefix = placeholderAnn.value();
            }

            RelationalPlaceholder relationalAnn = method.getDeclaredAnnotation(RelationalPlaceholder.class);
            if(relationalAnn != null) relational = true;

            List<PlaceholderParameter> params = new ArrayList<>();
            Parameter[] methodParams = method.getParameters();
            for(int i = relational ? 2 : 1; i < methodParams.length; i++) {
                Parameter methodParam = methodParams[i];
                String name = methodParam.getName();
                int size = 1;

                PlaceholderParam paramAnn = methodParam.getDeclaredAnnotation(PlaceholderParam.class);
                if(paramAnn != null) {
                    size = paramAnn.size();
                    if(!paramAnn.value().isEmpty()) {
                        name = paramAnn.value();
                    }
                }

                params.add(new PlaceholderParameter(name,size,methodParam.getType()));
            }

            placeholders.putIfAbsent(prefix, new ArrayList<>());
            placeholders.get(prefix).add(new PlaceholderInfo(prefix,description,relational,method,params));
        }
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String s) {
        return handlePlaceholder(Arrays.asList(player),s,false);
    }

    @Override
    public String onPlaceholderRequest(Player one, Player two, String s) {
        return handlePlaceholder(Arrays.asList(one,two),s,true);
    }

    private String handlePlaceholder(List<Object> baseParams, String s, boolean relational) {
        final PlaceholderInfo[] foundPlaceholder = {null};
        this.placeholders.forEach((prefix, list) -> {
            if(foundPlaceholder[0] != null) return;;
            if(!s.startsWith(prefix)) return;
            List<String> params = s.length() == prefix.length() ?
                    new ArrayList<>() :
                    Arrays.stream(s.substring(prefix.length()).split("_")).collect(Collectors.toList());

            list.forEach(placeholder -> {
                if(foundPlaceholder[0] != null) return;;
                if(placeholder.isRelational() != relational) return;
                if(params.size() < placeholder.getSize()) return;

                final int[] i = {0};
                final boolean[] found = {true};
                placeholder.getParams().forEach(param -> {
                    if(!found[0]) return;
                    String createdParam = String.join("_",params.subList(i[0], i[0] == placeholder.getSize() ? params.size() : i[0] +param.getSize()));
                    if(!ParameterHandler.getInstance().canConvert(createdParam, param.getExpected(), plugin)) {
                        found[0] = false;
                    }
                    i[0] += param.getSize();
                });

                if(found[0]) {
                    foundPlaceholder[0] = placeholder;
                }
            });
        });

        if(foundPlaceholder[0] != null) {
            List<String> placeholderParams = s.length() == foundPlaceholder[0].getPrefix().length() ?
                    new ArrayList<>() :
                    Arrays.stream(s.substring(foundPlaceholder[0].getPrefix().length()).split("_")).collect(Collectors.toList());
            List<Object> params = new ArrayList<>();
            params.addAll(baseParams);

            final int[] i = {0};
            foundPlaceholder[0].getParams().forEach(param -> {
                String createdParam = String.join("_",placeholderParams.subList(i[0], i[0] == foundPlaceholder[0].getSize() ? placeholderParams.size() : i[0] +param.getSize()));
                params.add(ParameterHandler.getInstance().convert(createdParam,param.getExpected(),plugin));
                i[0] += param.getSize();
            });

            try {
                return String.valueOf(foundPlaceholder[0].getMethod()
                        .invoke(this,params.stream().toArray()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override public boolean persist() {return this.persist;}
    @Override public boolean canRegister() {return this.canRegister;}

    public void setPersist(boolean persist) {this.persist = persist;}
    public void setCanRegister(boolean canRegister) {this.canRegister = canRegister;}

    @Override public @NotNull String getIdentifier() {return this.identifier;}
    @Override public @NotNull String getAuthor() {return this.author;}
    @Override public @NotNull String getVersion() {return this.version;}

    public void setAuthor(String author) {this.author = author;}
    public void setVersion(String version) {this.version = version;}
}
