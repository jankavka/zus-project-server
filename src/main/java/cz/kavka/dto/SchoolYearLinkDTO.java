package cz.kavka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Admin-configurable external URL for the "Organizace školního roku" item in the
 * "Úřední deska" submenu. An empty {@code url} means the menu item is hidden.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolYearLinkDTO {

    private String url = "";
}
