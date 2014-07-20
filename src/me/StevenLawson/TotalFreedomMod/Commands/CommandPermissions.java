package me.StevenLawson.TotalFreedomMod.Commands;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermissions
{
  AdminLevel level();
  
  DonatorLevel donorLevel() default DonatorLevel.NONE;
  
  SourceType source();
  
  boolean block_host_console() default false;
}
