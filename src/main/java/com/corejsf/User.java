package com.corejsf;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class User implements Serializable {
  private String country;
  public String getCountry() { return country; }
  public void setCountry(String country) { this.country = country; }
}
