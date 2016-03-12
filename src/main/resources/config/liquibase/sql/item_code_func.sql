CREATE FUNCTION item_code(id BIGINT)
    RETURNS VARCHAR(14) AS $code$

DECLARE
    alphabet VARCHAR(24);
    base     INTEGER DEFAULT 0;
    code     VARCHAR(14);
    divisor  BIGINT;
    mod      INTEGER DEFAULT 0;

BEGIN
    alphabet := '3KMEQPNHABTGCWUVRYZFSXJD';
    base := char_length(alphabet);
    code := '';

    WHILE id >= base LOOP
        divisor := (id / base) :: BIGINT;
        mod := (id - (base * divisor)) :: INTEGER;
        code := concat(substring(alphabet FROM mod + 1 FOR 1), code);
        id := divisor;
    END LOOP;

    IF id > 0
    THEN
        code = concat(substring(alphabet FROM (id :: INTEGER) + 1 FOR 1), code);
    END IF;

    RETURN (code);

END; $code$
LANGUAGE PLPGSQL;
