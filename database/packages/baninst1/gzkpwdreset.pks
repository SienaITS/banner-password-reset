CREATE OR REPLACE PACKAGE BANINST1.gzkpwdreset
AS
   PROCEDURE p_unlock_account (p_username VARCHAR2);

   FUNCTION f_generate_password (p_length              NUMBER DEFAULT 20,
                                 p_special_char_req    NUMBER DEFAULT 1)
      RETURN VARCHAR2;

   PROCEDURE p_reset_password (p_username   IN     VARCHAR2,
                               p_password      OUT VARCHAR2);
                               
   FUNCTION f_reset_ssb_pin (p_pidm IN NUMBER,
                             p_pin_length IN NUMBER DEFAULT 15)
   RETURN VARCHAR2;

END gzkpwdreset;