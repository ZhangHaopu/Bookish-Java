-- MySQL Script generated by MySQL Workbench
-- Thu Jul  8 19:09:12 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Books` (
  `id_books` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `isbn` VARCHAR(45) NULL,
  PRIMARY KEY (`id_books`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Members` (
  `id_members` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id_members`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CopiesOfBook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`CopiesOfBook` (
  `id_copyofbooks` INT NOT NULL AUTO_INCREMENT,
  `deletedDate` DATETIME NULL,
  `id` INT NULL,
  `available` TINYINT NULL,
  PRIMARY KEY (`id_copyofbooks`),
  INDEX `copiesTobooks_idx` (`id` ASC) VISIBLE,
  CONSTRAINT `copiesTobooks`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`Books` (`id_books`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transactions` (
  `id_transactions` INT NOT NULL AUTO_INCREMENT,
  `userid` INT NULL,
  `bookid` INT NULL,
  `outDate` DATETIME NULL,
  `dueDate` DATETIME NULL,
  `returnDate` DATETIME NULL,
  PRIMARY KEY (`id_transactions`),
  UNIQUE INDEX `outDate_UNIQUE` (`outDate` ASC) VISIBLE,
  INDEX `tranTomember_idx` (`userid` ASC) VISIBLE,
  INDEX `tranTocopy_idx` (`bookid` ASC) VISIBLE,
  CONSTRAINT `tranTomember`
    FOREIGN KEY (`userid`)
    REFERENCES `mydb`.`Members` (`id_members`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `tranTocopy`
    FOREIGN KEY (`bookid`)
    REFERENCES `mydb`.`CopiesOfBook` (`id_copyofbooks`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
