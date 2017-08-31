CREATE OR REPLACE PACKAGE BODY BANINST1.gzkpwdreset
AS
   PROCEDURE p_unlock_account (p_username VARCHAR2)
   IS
   BEGIN
      EXECUTE IMMEDIATE
            'ALTER USER '
         || DBMS_ASSERT.ENQUOTE_NAME (DBMS_ASSERT.SCHEMA_NAME (p_username),
                                      TRUE)
         || ' ACCOUNT UNLOCK';
   END p_unlock_account;

   FUNCTION f_generate_password (p_length              NUMBER DEFAULT 20,
                                 p_special_char_req    NUMBER DEFAULT 1)
      RETURN VARCHAR2
   IS
      pwd             VARCHAR2 (32767);
      x               NUMBER;
      temp            NUMBER;
      --pos NUMBER;
      special_chars   VARCHAR2 (32767) := '!%*+-./:?[]^_\{}|~';
   BEGIN
      IF p_special_char_req NOT IN (1, 0)
      THEN
         RAISE_APPLICATION_ERROR (
            -20001,
            'parameter p_special_char_req must be a 1 or 0.');
      END IF;

      -- ASCII codes
      -- A-Z 65-90
      -- a-z 97-122
      -- 0-9 48-57
      --large range of acceptable characters.... 32- 126
      -- ASCII CHARACTERS we can't use because of banner password requirements
      -- space = 32
      -- " - 34
      -- # - 35
      -- $ - 36
      -- & - 38
      -- ( - 40
      -- ) - 41
      -- , - 44
      -- ; - 59
      -- < - 60
      -- = - 61
      -- > - 62
      -- @ - 64
      -- ` - 96
      LOOP
         CASE
            WHEN (LENGTH (pwd) >= 2 AND NOT REGEXP_LIKE (pwd, '[a-z]'))
            THEN
               x := TRUNC (DBMS_RANDOM.VALUE (97, 123)); --generate lower case letter
            WHEN (LENGTH (pwd) >= 2 AND NOT REGEXP_LIKE (pwd, '[A-Z]'))
            THEN
               x := TRUNC (DBMS_RANDOM.VALUE (65, 91)); --generate upper case letter
            WHEN (LENGTH (pwd) >= 2 AND NOT REGEXP_LIKE (pwd, '[0-9]'))
            THEN
               x := TRUNC (DBMS_RANDOM.VALUE (48, 58));      --generate number
            WHEN (    p_special_char_req = 1
                  AND (    LENGTH (pwd) >= 2
                       AND NOT REGEXP_LIKE (pwd, '[^a-zA-Z0-9]')))
            THEN
               --put all specials chars we allow into special_chars string (char array);
               --randomly pick one from array
               x := TRUNC (DBMS_RANDOM.VALUE (1, LENGTH (special_chars) + 1)); --randomly pick index in special_chars
               x := ASCII (SUBSTR (special_chars, x, 1)); --convert specific char in special chars to ascii code so I can reuse regexp_replace statement
            ELSE
               IF p_special_char_req = 1         --special characters required
               THEN
                  x := TRUNC (DBMS_RANDOM.VALUE (32, 127));
               ELSE                           --no special characters required
                  temp := TRUNC (DBMS_RANDOM.VALUE (1, 4)); --pick a number 1 thru 3

                  CASE (temp)
                     WHEN 1
                     THEN
                        x := TRUNC (DBMS_RANDOM.VALUE (48, 58)); --generate number
                     WHEN 2
                     THEN
                        x := TRUNC (DBMS_RANDOM.VALUE (97, 123)); --generate lower case letter
                     WHEN 3
                     THEN
                        x := TRUNC (DBMS_RANDOM.VALUE (65, 91)); --generate upper case letter
                  END CASE;
               END IF;
         END CASE;

         IF p_special_char_req = 1                          --if special chars
         THEN
            CASE CHR (x)
               --certain special characters cause errors with the banner application.
               --The banner password policy prevents the following special characters from being used
               --when the character generated is any of the special characters that cannot used skip over that character and generate a new character
               WHEN '$'
               THEN
                  CONTINUE;
               WHEN '&'
               THEN
                  CONTINUE;
               WHEN '"'
               THEN
                  CONTINUE;
               WHEN '('
               THEN
                  CONTINUE;
               WHEN ')'
               THEN
                  CONTINUE;
               WHEN ','
               THEN
                  CONTINUE;
               WHEN '<'
               THEN
                  CONTINUE;
               WHEN '>'
               THEN
                  CONTINUE;
               WHEN '@'
               THEN
                  CONTINUE;
               WHEN '`'
               THEN
                  CONTINUE;
               WHEN ';'
               THEN
                  CONTINUE;
               WHEN '='
               THEN
                  CONTINUE;
               WHEN '#'
               THEN
                  CONTINUE;
               WHEN ' '
               THEN
                  CONTINUE;
               WHEN ''''
               THEN
                  CONTINUE;
               ELSE
                  pwd := pwd || CHR (x);
            END CASE;
         ELSE
            pwd := pwd || CHR (x);                          --no special chars
         END IF;

         EXIT WHEN LENGTH (pwd) = p_length;
      END LOOP;

      RETURN pwd;
   END f_generate_password;

   PROCEDURE p_reset_password (p_username   IN     VARCHAR2,
                               p_password      OUT VARCHAR2)
   IS
   BEGIN
      --unlock account
      p_unlock_account (p_username);

      --generate random password
      p_password := f_generate_password (20);

      --reset password for account
      EXECUTE IMMEDIATE
            'ALTER USER '
         || DBMS_ASSERT.ENQUOTE_NAME (
               DBMS_ASSERT.SCHEMA_NAME (UPPER (p_username)),
               TRUE)
         || ' IDENTIFIED BY '
         || DBMS_ASSERT.ENQUOTE_NAME (p_password, FALSE);

      --force user to change password on next login
      EXECUTE IMMEDIATE
            'ALTER USER '
         || DBMS_ASSERT.ENQUOTE_NAME (
               DBMS_ASSERT.SCHEMA_NAME (UPPER (p_username)),
               TRUE)
         || ' PASSWORD EXPIRE';
   END p_reset_password;


   FUNCTION f_reset_ssb_pin (p_pidm         IN NUMBER,
                             p_pin_length   IN NUMBER DEFAULT 15)
      RETURN VARCHAR2
   IS
      PRAGMA AUTONOMOUS_TRANSACTION;
      pin   gobtpac.gobtpac_pin%TYPE;
   BEGIN
      pin := gzkpwdreset.f_generate_password (p_pin_length, 0);
      gb_third_party_access.p_update (p_pidm               => p_pidm,
                                      p_pin                => pin,
                                      p_notification_ind   => 'N');
      COMMIT;
      RETURN pin;
   END f_reset_ssb_pin;
END gzkpwdreset;