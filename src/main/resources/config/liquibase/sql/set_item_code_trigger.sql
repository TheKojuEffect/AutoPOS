CREATE FUNCTION set_item_code()
    RETURNS TRIGGER AS
$BODY$
BEGIN
    NEW.code := item_code(NEW.id);
    RETURN NEW;
END;
$BODY$
LANGUAGE PLPGSQL;


CREATE TRIGGER set_item_code_trigger
BEFORE INSERT
ON item
FOR EACH ROW
EXECUTE PROCEDURE set_item_code();
